package gr.aueb.app.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "academicYears")
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "academicYear_id")
    private AcademicYear previousYear;

    @OneToMany(mappedBy = "academicYear", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ExaminationPeriod> examinationPeriods = new HashSet<>();

    protected AcademicYear(){};

    public AcademicYear(String name, Boolean isActive, AcademicYear previousYear) {
        this.name = name;
        this.isActive = isActive;
        this.previousYear = previousYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public AcademicYear getPreviousYear() {
        return previousYear;
    }

    public void setPreviousYear(AcademicYear previousYear) {
        this.previousYear = previousYear;
    }

    public Set<ExaminationPeriod> getExaminationPeriods() {
        return examinationPeriods;
    }

    public void setExaminationPeriods(Set<ExaminationPeriod> examinationPeriods) {
        this.examinationPeriods = examinationPeriods;
    }
}
