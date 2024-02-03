package gr.aueb.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "building", nullable = false, length = 50)
    private String building;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "general_capacity", nullable = false)
    private Integer generalCapacity;

    @Column(name = "exam_capacity", nullable = false)
    private Integer examCapacity;

    @Column(name = "covid_capacity", nullable = false)
    private Integer covidCapacity;

    @Column(name = "max_num_supervisors", nullable = false)
    private Integer maxNumSupervisors;

    protected Classroom() {}

    public Classroom(String name, String building, Integer floor, Integer generalCapacity, Integer examCapacity, Integer covidCapacity, Integer maxNumSupervisors) {
        this.name = name;
        this.building = building;
        this. floor = floor;
        this.generalCapacity = generalCapacity;
        this.examCapacity = examCapacity;
        this.covidCapacity = covidCapacity;
        this.maxNumSupervisors = maxNumSupervisors;
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getGeneralCapacity() {
        return generalCapacity;
    }

    public void setGeneralCapacity(Integer generalCapacity) {
        this.generalCapacity = generalCapacity;
    }

    public Integer getExamCapacity() {
        return examCapacity;
    }

    public void setExamCapacity(Integer examCapacity) {
        this.examCapacity = examCapacity;
    }

    public Integer getCovidCapacity() {
        return covidCapacity;
    }

    public void setCovidCapacity(Integer covidCapacity) {
        this.covidCapacity = covidCapacity;
    }

    public Integer getMaxNumSupervisors() {
        return maxNumSupervisors;
    }

    public void setMaxNumSupervisors(Integer maxNumSupervisors) {
        this.maxNumSupervisors = maxNumSupervisors;
    }
}
