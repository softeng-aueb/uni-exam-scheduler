package gr.aueb.app.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "courseAttendances")
public class CourseAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "attendance", nullable = false)
    private Integer attendance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected CourseAttendance(){};

    public CourseAttendance(Integer attendance, Course course, ExaminationPeriod examinationPeriod) {
        this.attendance = attendance;
        this.course = course;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public ExaminationPeriod getExaminationPeriod() {
        return examinationPeriod;
    }

    public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
        this.examinationPeriod = examinationPeriod;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
