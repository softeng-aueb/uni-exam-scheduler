package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("CS");
    }

    @Test
    void testCreateDepartment() {
        assertNotNull(department);
        assertEquals("CS", department.getName());
        assertTrue(department.getSubjects().isEmpty());
        assertTrue(department.getSupervisors().isEmpty());
    }

    @Test
    void testSettersandGetters() {
        Set<Subject> subjectSet = new HashSet<>();
        Set<Supervisor> supervisorSet = new HashSet<>();
        department.setId(5);
        department.setName("Math");
        department.setSubjects(subjectSet);
        department.setSupervisors(supervisorSet);

        assertEquals(5, department.getId());
        assertEquals("Math", department.getName());
        assertEquals(subjectSet, department.getSubjects());
        assertEquals(supervisorSet, department.getSupervisors());
    }

    @Test
    void testAddSubject() {
        Subject newSubject = new Subject("Algorithms", "CS110");
        department.addSubject(newSubject);

        assertTrue(department.getSubjects().contains(newSubject));
        assertTrue(newSubject.getDepartments().contains(department));
    }

    @Test
    void testRemoveSubject() {
        Subject newSubject = new Subject("Algorithms", "CS110");
        department.getSubjects().add(newSubject);

        department.removeSubject(newSubject);

        assertFalse(department.getSubjects().contains(newSubject));
        assertFalse(newSubject.getDepartments().contains(department));
    }
}
