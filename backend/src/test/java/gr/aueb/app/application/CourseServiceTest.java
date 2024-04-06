package gr.aueb.app.application;

import gr.aueb.app.domain.Course;
import gr.aueb.app.domain.Department;
import gr.aueb.app.persistence.CourseRepository;

import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CourseServiceTest {

    @Inject
    CourseService courseService;

    @Inject
    CourseRepository courseRepository;

    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("CS");
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllCourses() {
        List<Course> foundCourses = courseService.findAll();
        assertNotNull(foundCourses);
        assertEquals(2, foundCourses.size());
    }


    @Test
    @TestTransaction
    @Transactional
    public void testCreateCourse() {
        Course newCourse = new Course("Advanced Algorithms", "CS111", department);

        Course createdCourse = courseService.create(newCourse);

        assertNotNull(createdCourse);
        assertNotNull(createdCourse.getId());
        assertEquals("CS111", createdCourse.getCourseCode());
        assertEquals("Advanced Algorithms", createdCourse.getTitle());
        assertEquals("CS", createdCourse.getDepartment().getName());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateCourse() {
        Course updateCourse = new Course("Intro to Programming With C", "CS105", department);
        Course updatedCourse = courseService.update(4001, updateCourse);

        assertNotNull(updatedCourse);
        assertEquals(4001, updatedCourse.getId());
        assertEquals("Intro to Programming With C", updatedCourse.getTitle());
        assertEquals("CS105", updatedCourse.getCourseCode());
        assertEquals("CS", updatedCourse.getDepartment().getName());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testDeleteCourse() {
        courseService.delete(4002);

        Course deletedCourse = courseRepository.findById(4002);
        List<Course> foundCourses = courseRepository.listAll();

        assertNull(deletedCourse);
        assertEquals(1, foundCourses.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneCourse() {
        Course foundCourse = courseService.findOne(4002);
        assertNotNull(foundCourse);
        assertEquals(4002, foundCourse.getId());
        assertEquals("AF101", foundCourse.getCourseCode());
        assertEquals("Accounting and Finance 101", foundCourse.getTitle());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateCourseFailed() {
        Course updateCourse = new Course("Intro to Programming With C", "CS105", department);
        assertThrows(NotFoundException.class, () ->
                courseService.update(999, updateCourse));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindCourseFailed() {
        assertThrows(NotFoundException.class, () ->
                courseService.findOne(999));
    }
}

