package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupervisorTest {

    private Supervisor supervisor;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("CS");
        supervisor = new Supervisor("John", "Doe", "O'Reily", "123456789", "john.doe@example.com", SupervisorCategory.EDIP, department);
    }

    @Test
    void testCreateSupervisor() {
        assertNotNull(supervisor);
        assertEquals("John", supervisor.getName());
        assertEquals("Doe", supervisor.getSurname());
        assertEquals("O'Reily", supervisor.getSupervisor());
        assertEquals("123456789", supervisor.getTelephone());
        assertEquals("john.doe@example.com", supervisor.getEmail());
        assertEquals(SupervisorCategory.EDIP, supervisor.getSupervisorCategory());
        assertEquals("CS", supervisor.getDepartment().getName());
        assertEquals("john.doe@example.com", supervisor.toString());
    }

    @Test
    void testSettersAndGetters() {
        Department newDepartment = new Department("Pliroforiki");
        supervisor.setId(42);
        supervisor.setName("Jane");
        supervisor.setSurname("Smith");
        supervisor.setSupervisor("Roland");
        supervisor.setTelephone("987654321");
        supervisor.setEmail("jane.smith@example.com");
        supervisor.setSupervisorCategory(SupervisorCategory.ETEP);
        supervisor.setDepartment(newDepartment);

        assertEquals(42, supervisor.getId());
        assertEquals("Jane", supervisor.getName());
        assertEquals("Smith", supervisor.getSurname());
        assertEquals("Roland", supervisor.getSupervisor());
        assertEquals("987654321", supervisor.getTelephone());
        assertEquals("jane.smith@example.com", supervisor.getEmail());
        assertEquals(SupervisorCategory.ETEP, supervisor.getSupervisorCategory());
        assertEquals(newDepartment, supervisor.getDepartment());
    }
}
