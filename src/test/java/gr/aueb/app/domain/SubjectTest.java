package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectTest {

    private Subject subject;

    @BeforeEach
    void setup() {
        subject = new Subject("Algorithms", "CS103");
    }

    @Test
    void testCreateSubject() {
        assertNotNull(subject);
        assertEquals("Algorithms", subject.getTitle());
        assertEquals("CS103", subject.getSubjectCode());
        assertTrue(subject.getDepartments().isEmpty());
    }

    @Test
    void testSettersandGetters() {
        Set<Department> departments = new HashSet<>();
        subject.setId(10);
        subject.setSubjectCode("CS110");
        subject.setTitle("OOP");
        subject.setDepartments(departments);

        assertEquals(10, subject.getId());
        assertEquals("CS110", subject.getSubjectCode());
        assertEquals("OOP", subject.getTitle());
        assertEquals(departments, subject.getDepartments());
        assertTrue(subject.getDepartments().isEmpty());
    }

    @Test
    void testAddDeparment() {
        Department newDepartment = new Department();
        subject.addDepartment(newDepartment);

        assertTrue(subject.getDepartments().contains(newDepartment));
    }

    @Test
    void testRemoveDepartment() {
        Department newDepartment = new Department();
        subject.getDepartments().add(newDepartment);

        subject.removeDepartment(newDepartment);

        assertFalse(subject.getDepartments().contains(newDepartment));
    }
}
