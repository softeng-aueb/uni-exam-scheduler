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
    private Integer general_capacity;

    @Column(name = "exam_capacity", nullable = false)
    private Integer exam_capacity;

    @Column(name = "covid_capacity", nullable = false)
    private Integer covid_capacity;

    @Column(name = "max_num_supervisors", nullable = false)
    private Integer max_num_supervisors;

    protected Classroom() {}

    public Classroom(String name, String building, Integer floor, Integer general_capacity, Integer exam_capacity, Integer covid_capacity, Integer max_num_supervisors) {
        this.name = name;
        this.building = building;
        this. floor = floor;
        this.general_capacity = general_capacity;
        this.exam_capacity = exam_capacity;
        this.covid_capacity = covid_capacity;
        this.max_num_supervisors = max_num_supervisors;
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

    public Integer getGeneral_capacity() {
        return general_capacity;
    }

    public void setGeneral_capacity(Integer general_capacity) {
        this.general_capacity = general_capacity;
    }

    public Integer getExam_capacity() {
        return exam_capacity;
    }

    public void setExam_capacity(Integer exam_capacity) {
        this.exam_capacity = exam_capacity;
    }

    public Integer getCovid_capacity() {
        return covid_capacity;
    }

    public void setCovid_capacity(Integer covid_capacity) {
        this.covid_capacity = covid_capacity;
    }

    public Integer getMax_num_supervisors() {
        return max_num_supervisors;
    }

    public void setMax_num_supervisors(Integer max_num_supervisors) {
        this.max_num_supervisors = max_num_supervisors;
    }
}
