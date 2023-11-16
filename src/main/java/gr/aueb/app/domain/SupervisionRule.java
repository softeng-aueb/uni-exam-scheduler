package gr.aueb.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "supervisionRules")
public class SupervisionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "num_of_supervisions", nullable = false)
    private Integer num_of_supervisions;

    @Enumerated(EnumType.STRING)
    @Column(name = "supervisor_category", nullable = false)
    private SupervisorCategory supervisor_category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected SupervisionRule(){};

    public SupervisionRule(Integer num_of_supervisions, SupervisorCategory supervisor_category, Department department, ExaminationPeriod examinationPeriod) {
        this.num_of_supervisions = num_of_supervisions;
        this.supervisor_category = supervisor_category;
        this.department = department;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum_of_supervisions() {
        return num_of_supervisions;
    }

    public void setNum_of_supervisions(Integer num_of_supervisions) {
        this.num_of_supervisions = num_of_supervisions;
    }

    public SupervisorCategory getSupervisor_category() {
        return supervisor_category;
    }

    public void setSupervisor_category(SupervisorCategory supervisor_category) {
        this.supervisor_category = supervisor_category;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public ExaminationPeriod getExaminationPeriod() {
        return examinationPeriod;
    }

    public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
        this.examinationPeriod = examinationPeriod;
    }
}
