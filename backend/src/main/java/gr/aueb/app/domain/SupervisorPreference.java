package gr.aueb.app.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supervisorPreferences")
public class SupervisorPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "supervisor_preference_exclude_dates",
            joinColumns = @JoinColumn(name = "supervisor_preference_id")
    )
    @Column(name = "exclude_date")
    private Set<LocalDate> excludeDates = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected  SupervisorPreference(){};

    public SupervisorPreference(LocalTime startTime, LocalTime endTime, Set<LocalDate> excludeDates, Supervisor supervisor, ExaminationPeriod examinationPeriod) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.excludeDates = excludeDates;
        this.supervisor = supervisor;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<LocalDate> getExcludeDates() {
        return excludeDates;
    }

    public void setExcludeDates(Set<LocalDate> excludeDates) {
        this.excludeDates = excludeDates;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public ExaminationPeriod getExaminationPeriod() {
        return examinationPeriod;
    }

    public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
        this.examinationPeriod = examinationPeriod;
    }
}
