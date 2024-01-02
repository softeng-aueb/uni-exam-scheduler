package gr.aueb.app.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "examinations")
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "required_supervisors", nullable = false)
    private Integer requiredSupervisors;

    @Transient
    private Integer totalDeclaration;

    @Transient
    private Integer totalAttendance;

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DepartmentParticipation> departmentParticipations = new HashSet<>();

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Supervision> supervisions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "examinations_classrooms",
            joinColumns = @JoinColumn(name = "examination_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private Set<Classroom> classrooms;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected Examination(){};

    public Examination(LocalDate startDate, LocalDate endDate, Subject subject, Set<Classroom> classrooms, ExaminationPeriod examinationPeriod) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.subject = subject;
        this.classrooms = classrooms;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getRequiredSupervisors() {
        return requiredSupervisors;
    }

    public void setRequiredSupervisors(Integer requiredSupervisors) {
        this.requiredSupervisors = requiredSupervisors;
    }

    public Set<DepartmentParticipation> getDepartmentParticipations() {
        return departmentParticipations;
    }

    public void setDepartmentParticipations(Set<DepartmentParticipation> departmentParticipations) {
        this.departmentParticipations = departmentParticipations;
    }

    public Set<Supervision> getSupervisions() {
        return supervisions;
    }

    public void setSupervisions(Set<Supervision> supervisions) {
        this.supervisions = supervisions;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        return departmentParticipations.stream()
                .map(DepartmentParticipation::getDeclaration)
                .reduce(0, Integer::sum);
    }

    public Integer getTotalAttendance() {
        return departmentParticipations.stream()
                .map(DepartmentParticipation::getAttendance)
                .reduce(0, Integer::sum);
    }

    public void addSupervision(Supervision supervision) {
        supervision.setExamination(this);
        this.supervisions.add(supervision);
    }

    public void removeSupervision(Supervision supervision) {
        supervision.setExamination(null);
        supervisions.remove(supervision);
    }

    public void addDepartmentParticipation(DepartmentParticipation departmentParticipation) {
        departmentParticipation.setExamination(this);
        this.departmentParticipations.add(departmentParticipation);
    }

    public void removeDepartmentParticipation(DepartmentParticipation departmentParticipation) {
        departmentParticipation.setExamination(null);
        departmentParticipations.remove(departmentParticipation);
    }
}
