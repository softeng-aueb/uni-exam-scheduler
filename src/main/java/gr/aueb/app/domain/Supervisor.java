package gr.aueb.app.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supervisors")
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "supervisor", nullable = false, length = 50)
    private String supervisor;

    @Column(name = "telephone", nullable = false, length = 50)
    private String telephone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "supervisor_category", nullable = false)
    private SupervisorCategory supervisorCategory;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Supervision> supervisions = new HashSet<>();

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

    public Set<Supervision> getSupervisions() {
        return supervisions;
    }

    public void setSupervisions(Set<Supervision> supervisions) {
        this.supervisions = supervisions;
    }

    public void addSupervision(Supervision supervision) {
        supervision.setSupervisor(this);
        this.supervisions.add(supervision);
    }

    public void removeSupervision(Supervision supervision) {
        supervision.setSupervisor(null);
        this.supervisions.remove(supervision);
    }
}
