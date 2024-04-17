package gr.aueb.app.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "required_supervisors", nullable = false)
    private Integer requiredSupervisors = 0;

    @Transient
    private Integer totalDeclaration;

    @Transient
    private Integer totalAttendance;

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

    protected Examination(){};

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

    public Integer getRequiredSupervisors() {
        return requiredSupervisors;
    }

    public void setRequiredSupervisors(Integer requiredSupervisors) {
        this.requiredSupervisors = requiredSupervisors;
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

    public Integer getTotalDeclaration() {
//        return departmentParticipations.stream()
//                .map(DepartmentParticipation::getDeclaration)
//                .reduce(0, Integer::sum);
        return totalDeclaration;
    }

    public Integer getTotalAttendance() {
//        return departmentParticipations.stream()
//                .map(DepartmentParticipation::getAttendance)
//                .reduce(0, Integer::sum);
        return totalAttendance;
    }

    public Supervision addSupervision(Supervisor supervisor, List<Supervision> supervisorSupervisions) {
        // check if there are available slots
        if (this.getRequiredSupervisors() < (this.getSupervisions().size() + 1)) {
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
