package gr.aueb.app.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "courseDeclarations")
public class CourseDeclaration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "declaration", nullable = false)
    private Integer declaration;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JoinColumn(name = "academicYear_id")
    private AcademicYear academicYear;

    protected CourseDeclaration(){};

    public CourseDeclaration(Integer declaration, Course course, AcademicYear academicYear) {
        this.declaration = declaration;
        this.course = course;
        this.academicYear = academicYear;
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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
