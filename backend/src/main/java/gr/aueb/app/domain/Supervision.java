package gr.aueb.app.domain;

import jakarta.persistence.*;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
@Entity
@Table(name = "supervisions")
public class Supervision {

    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "isPresent")
    private Boolean isPresent = true;

    @Column(name = "isLead", nullable = false)
    private Boolean isLead = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @PlanningVariable
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    protected Supervision(){};

    public Supervision(Examination examination) {
        this.examination = examination;
    }

    @Override
    public String toString() {
        return "(" + id + ")";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Boolean getIsLead() {
        return isLead;
    }

    public void setIsLead(Boolean isLead) {
        this.isLead = isLead;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
