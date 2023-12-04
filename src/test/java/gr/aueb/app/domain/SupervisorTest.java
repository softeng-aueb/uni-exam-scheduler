package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupervisorTest {

    private Supervisor supervisor;

    @BeforeEach
    void setUp() {
        supervisor = new Supervisor("John", "Doe", "O'Reily", "123456789", "john.doe@example.com", SupervisorCategory.EDIP, new Department());
    }

    @Test
    void testCreateSupervisor() {
        assertNotNull(supervisor);
        assertEquals("John", supervisor.getName());
        assertEquals("Doe", supervisor.getSurname());
        assertEquals("O'Reily", supervisor.getSupervisor());
        assertEquals("123456789", supervisor.getTelephone());
        assertEquals("john.doe@example.com", supervisor.getEmail());
        assertEquals(SupervisorCategory.EDIP, supervisor.getSupervisor_category());
        assertNotNull(supervisor.getDepartment());
        assertNotNull(supervisor.getSupervisions());
        assertTrue(supervisor.getSupervisions().isEmpty());
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
        supervisor.setSupervisor_category(SupervisorCategory.ETEP);
        supervisor.setDepartment(newDepartment);
        supervisor.setSupervisions(null);

        assertEquals(42, supervisor.getId());
        assertEquals("Jane", supervisor.getName());
        assertEquals("Smith", supervisor.getSurname());
        assertEquals("Roland", supervisor.getSupervisor());
        assertEquals("987654321", supervisor.getTelephone());
        assertEquals("jane.smith@example.com", supervisor.getEmail());
        assertEquals(SupervisorCategory.ETEP, supervisor.getSupervisor_category());
        assertEquals(newDepartment, supervisor.getDepartment());
        assertNull(supervisor.getSupervisions());
    }

    @Test
    void testAddSupervision() {
        Supervision supervision = new Supervision(false, new Examination(), null);
        supervisor.addSupervision(supervision);

        assertTrue(supervisor.getSupervisions().contains(supervision));
        assertEquals(supervisor, supervision.getSupervisor());
    }

    @Test
    void testRemoveSupervision() {
        Supervision supervision = new Supervision(false, new Examination(), supervisor);
        supervisor.getSupervisions().add(supervision);
        supervisor.removeSupervision(supervision);

        assertTrue(supervisor.getSupervisions().isEmpty());
        assertNull(supervision.getSupervisor());
    }
}
