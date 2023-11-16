package gr.aueb.app.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "examinationPeriods")
public class ExaminationPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "semester", nullable = false)
    private Semester semester;

    @Column(name = "start_date", nullable = false)
    private LocalDate start_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    private Period period;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "academicYear_id")
    private AcademicYear academicYear;

    @OneToMany(mappedBy = "examinationPeriod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SupervisionRule> supervisionRules = new HashSet<>();

    protected ExaminationPeriod(){};

    public ExaminationPeriod(Semester semester, LocalDate start_date, Period period, AcademicYear academicYear, Set<SupervisionRule> supervisionRules) {
        this.semester = semester;
        this.start_date = start_date;
        this.period = period;
        this.academicYear = academicYear;
        this.supervisionRules = supervisionRules;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Set<SupervisionRule> getSupervisionRules() {
        return supervisionRules;
    }

    public void setSupervisionRules(Set<SupervisionRule> supervisionRules) {
        this.supervisionRules = supervisionRules;
    }

    public void addSupervisionRule(SupervisionRule supervisionRule) {
        supervisionRule.setExaminationPeriod(this);
        this.supervisionRules.add(supervisionRule);
    }

    public void removeSupervisionRule(Integer supervisionRuleId) {
        this.supervisionRules.removeIf(supervisionRule -> supervisionRule.getId().equals(supervisionRuleId));
    }
}
