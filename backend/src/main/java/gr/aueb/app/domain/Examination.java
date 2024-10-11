package gr.aueb.app.domain;

import gr.aueb.app.persistence.CourseAttendanceRepository;
import gr.aueb.app.persistence.CourseDeclarationRepository;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "examinations")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Transient
    private Integer maxSupervisors = 0 ;

    @Transient
    private Integer estimatedSupervisors = 0;

    @Transient
    private Integer declaration = 0;

    @Transient
    private Integer estimatedAttendance = 0;

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Supervision> supervisions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(name = "examinations_classrooms",
            joinColumns = @JoinColumn(name = "examination_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private Set<Classroom> classrooms;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected Examination(){}

    public Examination(LocalDate date, LocalTime startTime, LocalTime endTime, Course course, Set<Classroom> classrooms, ExaminationPeriod examinationPeriod) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.classrooms = classrooms;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<Supervision> getSupervisions() {
        return supervisions;
    }

    public void setSupervisions(Set<Supervision> supervisions) {
        this.supervisions = supervisions;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public ExaminationPeriod getExaminationPeriod() {
        return examinationPeriod;
    }

    public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
        this.examinationPeriod = examinationPeriod;
    }

    public void setMaxSupervisors(Integer maxSupervisors) {
        this.maxSupervisors = maxSupervisors;
    }

    public Integer getMaxSupervisors() {
        Integer sum = 0;
        for(Classroom classroom : this.getClassrooms()) {
            sum += classroom.getMaxNumSupervisors();
        }
        return sum;
    }

    public Integer getEstimatedSupervisors() {
        // formula: 1 Supervisor for 30 students
        Integer div = this.estimatedAttendance/30;
        return div == 0 ? 1 : div;
    }

    public Integer getDeclaration() {
        return declaration;
    }

    public void setDeclaration(CourseDeclarationRepository repository) {
        CourseDeclaration courseDeclaration = repository.findSpecific(this.course.getId(), this.examinationPeriod.getAcademicYear().getId());
        this.declaration = courseDeclaration != null ? courseDeclaration.getDeclaration() : 0;
    }

    public Integer getEstimatedAttendance() {
        return estimatedAttendance;
    }

    public void setEstimatedAttendance(CourseDeclarationRepository courseDeclarationRepository, CourseAttendanceRepository courseAttendanceRepository) {
        // get declaration and attendance from 2 years back
        AcademicYear previousOneYear = this.examinationPeriod.getAcademicYear().getPreviousYear();
        AcademicYear previousTwoYears = previousOneYear != null ? previousOneYear.getPreviousYear() : null;
        CourseDeclaration previousOneDeclaration = previousOneYear != null ? courseDeclarationRepository.findSpecific(this.course.getId(), previousOneYear.getId()) : null;
        CourseDeclaration previousTwoDeclaration = previousTwoYears != null ? courseDeclarationRepository.findSpecific(this.course.getId(), previousTwoYears.getId()) : null;
        CourseAttendance previousOneAttendance = previousOneYear != null ? courseAttendanceRepository.findSpecific(this.course.getId(), previousOneYear.getId(), this.examinationPeriod.getPeriod()) : null;
        CourseAttendance previousTwoAttendance = previousTwoYears != null ? courseAttendanceRepository.findSpecific(this.course.getId(), previousTwoYears.getId(), this.examinationPeriod.getPeriod()) : null;

        // estimation formula
        // if previousOne/TwoYear data is missing assuming that 4/5 was attended
        Double previousOnePercentage = ( previousOneDeclaration == null || previousOneAttendance == null ) ? (double) 4/5 : (double) previousOneAttendance.getAttendance()/previousOneDeclaration.getDeclaration();
        Double previousTwoPercentage = ( previousTwoDeclaration == null || previousTwoAttendance == null ) ? (double) 4/5 : (double) previousTwoAttendance.getAttendance()/previousTwoDeclaration.getDeclaration();
        this.estimatedAttendance = (int) Math.round(this.declaration * (previousOnePercentage + previousTwoPercentage) / 2);
    }

    public Supervision addSupervision(Supervisor supervisor, List<Supervision> supervisorSupervisions) {
        // check if there are available slots
        if (this.getMaxSupervisors() < (this.getSupervisions().size() + 1)) {
            return null;
        }

        // check if supervisor is already on the list
        if (this.getSupervisions().stream().anyMatch(supervision -> supervision.getSupervisor().equals(supervisor))) {
            return null;
        }

        // check if supervisor has overlapping supervision
       if (this.hasOverlap(supervisorSupervisions)) {
           return null;
       }

        Supervision newSupervision = new Supervision(this);
        newSupervision.setSupervisor(supervisor);
        this.supervisions.add(newSupervision);
        return newSupervision;
    }

    public void removeSupervision(Supervision supervision) {
        supervision.setExamination(null);
        supervisions.remove(supervision);
    }

    private boolean hasOverlap(List<Supervision> supervisionList) {
        LocalTime examStartTime = this.getStartTime();
        LocalTime examEndTime = this.getEndTime();

        return supervisionList.stream()
                .anyMatch(supervision -> {
                    LocalTime superExamStartTime = supervision.getExamination().getStartTime();
                    LocalTime superExamEndTime = supervision.getExamination().getEndTime();

                    return (examStartTime.isBefore(superExamEndTime) || examStartTime.equals(superExamEndTime))
                            && (examEndTime.isAfter(superExamStartTime) || examEndTime.equals(superExamStartTime));
                });
    }

    public void removeClassroom(Classroom classroom) {
        this.getClassrooms().remove(classroom);
    }
}
