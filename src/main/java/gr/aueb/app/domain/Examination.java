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
    private LocalDate start_date;

    @Column(name = "end_date", nullable = false)
    private LocalDate end_date;

    @Column(name = "required_supervisors", nullable = false)
    private Integer required_supervisors;

    @Transient
    private Integer total_declaration;

    @Transient
    private Integer total_attendance;

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DepartmentParticipation> departmentParticipations = new HashSet<>();

    @OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Supervision> supervisions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "examination_classroom",
            joinColumns = @JoinColumn(name = "examination_id"),
            inverseJoinColumns = @JoinColumn(name = "classroom_id")
    )
    private Set<Classroom> classrooms = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected Examination(){};

    public Examination(LocalDate start_date, LocalDate end_date, Integer required_supervisors, Set<DepartmentParticipation> departmentParticipations, Subject subject, Set<Classroom> classrooms, ExaminationPeriod examinationPeriod) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.required_supervisors = required_supervisors;
        this.departmentParticipations = departmentParticipations;
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Integer getRequired_supervisors() {
        return required_supervisors;
    }

    public void setRequired_supervisors(Integer required_supervisors) {
        this.required_supervisors = required_supervisors;
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

    public Integer getTotal_declaration() {
        return departmentParticipations.stream()
                .map(DepartmentParticipation::getDeclaration)
                .reduce(0, Integer::sum);
    }

    public Integer getTotal_attendance() {
        return departmentParticipations.stream()
                .map(DepartmentParticipation::getAttendance)
                .reduce(0, Integer::sum);
    }

    public void addSupervision(Supervision supervision) {
        supervision.setExamination(this);
        this.supervisions.add(supervision);
    }

    public void removeSupervision(Integer supervisionId) {
        this.supervisions.removeIf(supervision -> supervision.getId().equals(supervisionId));
    }
}
