package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseAttendanceTest {

    private CourseAttendance courseAttendance;
    @BeforeEach
    void setup() {
        courseAttendance = new CourseAttendance(150, new Course(), new ExaminationPeriod());
    }

    @Test
    void testCreateCourseAttendance() {
        assertNotNull(courseAttendance);
        assertEquals(150, courseAttendance.getAttendance());
        assertNotNull(courseAttendance.getCourse());
        assertNotNull(courseAttendance.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Course newCourse = new Course();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        courseAttendance.setId(3);
        courseAttendance.setAttendance(170);
        courseAttendance.setCourse(newCourse);
        courseAttendance.setExaminationPeriod(newExaminationPeriod);

        assertEquals(3, courseAttendance.getId());
        assertEquals(170, courseAttendance.getAttendance());
        assertEquals(newCourse, courseAttendance.getCourse());
        assertEquals(newExaminationPeriod, courseAttendance.getExaminationPeriod());
    }
}
