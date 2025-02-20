package gr.aueb.app.domain;

import jakarta.persistence.*;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Entity
@Table(name = "supervisors")
public class Supervisor {

    @PlanningId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "surname", nullable = true, length = 50)
    private String surname;

    @Column(name = "supervisor", nullable = true, length = 50)
    private String supervisor;

    @Column(name = "telephone", nullable = true, length = 50)
    private String telephone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "supervisor_category", nullable = false)
    private SupervisorCategory supervisorCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "department_id")
    private Department department;

    protected Supervisor(){};

    public Supervisor(String name, String surname, String supervisor, String telephone, String email, SupervisorCategory supervisorCategory, Department department) {
        this.name = name;
        this.surname = surname;
        this.supervisor = supervisor;
        this.telephone = telephone;
        this.email = email;
        this.supervisorCategory = supervisorCategory;
        this.department = department;
    }

    @Override
    public String toString() {
        return email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SupervisorCategory getSupervisorCategory() {
        return supervisorCategory;
    }

    public void setSupervisorCategory(SupervisorCategory supervisor_category) {
        this.supervisorCategory = supervisor_category;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
