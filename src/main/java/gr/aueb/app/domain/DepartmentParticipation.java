package gr.aueb.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "departmentsParticipations")
public class DepartmentParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "declaration", nullable = false)
    private Integer declaration;

    @Column(name = "attendance", nullable = false)
    private Integer attendance;

    @Column(name = "isLeadDepartment", nullable = false)
    private Boolean isLeadDepartment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "department_id")
    private Department department;

    protected DepartmentParticipation(){};

    public DepartmentParticipation(Integer declaration, Boolean isLeadDepartment, Examination examination, Department department) {
        this.declaration = declaration;
        this.isLeadDepartment = isLeadDepartment;
        this.examination = examination;
        this.department = department;
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
        isLeadDepartment = isLeadDepartment;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
