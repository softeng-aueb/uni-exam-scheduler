package gr.aueb.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "departmentParticipations")
public class DepartmentParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "declaration", nullable = false)
    private Integer declaration;

    @Column(name = "attendance", nullable = false)
    private Integer attendance = 0;

    @Column(name = "isLeadDepartment", nullable = false)
    private Boolean isLeadDepartment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "examinationPeriod_id")
    private ExaminationPeriod examinationPeriod;

    protected DepartmentParticipation(){};

    public DepartmentParticipation(Integer declaration, Boolean isLeadDepartment, Course course, Department department, ExaminationPeriod examinationPeriod) {
        this.declaration = declaration;
        this.isLeadDepartment = isLeadDepartment;
        this.course = course;
        this.department = department;
        this.examinationPeriod = examinationPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Integer declaration) {
        this.declaration = declaration;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Boolean getIsLeadDepartment() {
        return isLeadDepartment;
    }

    public void setIsLeadDepartment(Boolean isLeadDepartment) {
        this.isLeadDepartment = isLeadDepartment;
    }

    public ExaminationPeriod getExaminationPeriod() {
        return examinationPeriod;
    }

    public void setExaminationPeriod(ExaminationPeriod examinationPeriod) {
        this.examinationPeriod = examinationPeriod;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
