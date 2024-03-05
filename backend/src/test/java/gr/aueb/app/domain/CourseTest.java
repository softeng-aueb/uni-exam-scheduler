package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Course course;
    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("CS");
        course = new Course("Algorithms", "CS103", department);
    }

    @Test
    void testCreateCourse() {
        assertNotNull(course);
        assertEquals("Algorithms", course.getTitle());
        assertEquals("CS103", course.getCourseCode());
        assertEquals(department, course.getDepartment());
    }

    @Test
    void testSettersGetters() {
        Department department = new Department("Economics");
        course.setId(10);
        course.setCourseCode("CS110");
        course.setTitle("OOP");
        course.setDepartment(department);

        assertEquals(10, course.getId());
        assertEquals("CS110", course.getCourseCode());
        assertEquals("OOP", course.getTitle());
        assertEquals("Economics", course.getDepartment().getName());
    }
}
