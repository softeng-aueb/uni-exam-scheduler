package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Course course;

    @BeforeEach
    void setup() {
        course = new Course("Algorithms", "CS103");
    }

    @Test
    void testCreateCourse() {
        assertNotNull(course);
        assertEquals("Algorithms", course.getTitle());
        assertEquals("CS103", course.getCourseCode());
        assertNull(course.getDepartment());
    }

    @Test
    void testSettersandGetters() {
        Department department = new Department("CS");
        course.setId(10);
        course.setCourseCode("CS110");
        course.setTitle("OOP");
        course.setDepartment(department);

        assertEquals(10, course.getId());
        assertEquals("CS110", course.getCourseCode());
        assertEquals("OOP", course.getTitle());
        assertEquals(department, course.getDepartment());
    }
}
