package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void testSettersandGetters() {
        department.setId(5);
        department.setName("Math");

        assertEquals(5, department.getId());
        assertEquals("Math", department.getName());
    }
}
