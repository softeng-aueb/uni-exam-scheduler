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
        assertEquals("CS103", subject.getSubject_code());
        assertTrue(subject.getDepartments().isEmpty());
    }

    @Test
    void testSettersandGetters() {
        Set<Department> departments = new HashSet<>();
        subject.setId(10);
        subject.setSubject_code("CS110");
        subject.setTitle("OOP");
        subject.setDepartments(departments);

        assertEquals(10, subject.getId());
        assertEquals("CS110", subject.getSubject_code());
        assertEquals("OOP", subject.getTitle());
        assertEquals(departments, subject.getDepartments());
        assertTrue(subject.getDepartments().isEmpty());
    }
}
