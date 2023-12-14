package gr.aueb.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "supervisionRules")
public class SupervisionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "num_of_supervisions", nullable = false)
    private Integer numOfSupervisions;

    @Enumerated(EnumType.STRING)
    @Column(name = "supervisor_category", nullable = false)
    private SupervisorCategory supervisorCategory;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected SupervisionRule(){};

    public SupervisionRule(Integer numOfSupervisions, SupervisorCategory supervisorCategory, Department department, ExaminationPeriod examinationPeriod) {
        this.numOfSupervisions = numOfSupervisions;
        this.supervisorCategory = supervisorCategory;
        this.department = department;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumOfSupervisions() {
        return numOfSupervisions;
    }

    public void setNumOfSupervisions(Integer numOfSupervisions) {
        this.numOfSupervisions = numOfSupervisions;
    }

    public SupervisorCategory getSupervisorCategory() {
        return supervisorCategory;
    }

    public void setSupervisorCategory(SupervisorCategory supervisorCategory) {
        this.supervisorCategory = supervisorCategory;
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
