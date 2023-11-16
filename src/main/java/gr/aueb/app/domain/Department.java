package gr.aueb.app.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "department_subject",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<Supervisor> supervisors;

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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public Set<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(Set<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
        subject.getDepartments().add(this);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        subject.getDepartments().remove(this);
    }

    public void addSupervisor(Supervisor supervisor) {
        supervisors.add(supervisor);
        supervisor.setDepartment(this);
    }

    public void removeSupervisor(Supervisor supervisor) {
        supervisors.remove(supervisor);
        supervisor.setDepartment(null);
    }
}
