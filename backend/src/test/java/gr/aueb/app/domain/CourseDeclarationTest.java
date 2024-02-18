package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDeclarationTest {

    private CourseDeclaration courseDeclaration;
    @BeforeEach
    void setup() {
        courseDeclaration = new CourseDeclaration(200, new Course(), new AcademicYear());
    }

    @Test
    void testCreateCourseDeclaration() {
        assertNotNull(courseDeclaration);
        assertEquals(200, courseDeclaration.getDeclaration());
        assertNotNull(courseDeclaration.getCourse());
        assertNotNull(courseDeclaration.getAcademicYear());
    }

    @Test
    void testSettersAndGetters() {
        Course newCourse = new Course();
        AcademicYear newAcademicYear = new AcademicYear();
        courseDeclaration.setId(3);
        courseDeclaration.setDeclaration(170);
        courseDeclaration.setCourse(newCourse);
        courseDeclaration.setAcademicYear(newAcademicYear);

        assertEquals(3, courseDeclaration.getId());
        assertEquals(170, courseDeclaration.getDeclaration());
        assertEquals(newCourse, courseDeclaration.getCourse());
        assertEquals(newAcademicYear, courseDeclaration.getAcademicYear());
    }
}
