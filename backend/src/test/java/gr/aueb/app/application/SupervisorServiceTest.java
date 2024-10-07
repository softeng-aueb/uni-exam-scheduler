package gr.aueb.app.application;

import gr.aueb.app.domain.Department;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorCategory;
import gr.aueb.app.persistence.DepartmentRepository;
import gr.aueb.app.persistence.SupervisorRepository;

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
public class SupervisorServiceTest {

    @Inject
    SupervisorService supervisorService;

    @Inject
    SupervisorRepository supervisorRepository;

    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("CS");
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllSupervisors() {
        List<Supervisor> foundSupervisors = supervisorService.findAll();
        assertEquals(6, foundSupervisors.size());
    }


    @Test
    @TestTransaction
    @Transactional
    public void testCreateSupervisor() {
        Supervisor newSupervisor = new Supervisor("Super", "Visor", "supervisor", "1234", "supervisor@mail.com", SupervisorCategory.ETEP, department);

        Supervisor createdSupervisor = supervisorService.create(newSupervisor, department.getId());

        assertNotNull(createdSupervisor);
        assertNotNull(createdSupervisor.getId());
        assertEquals("Super", createdSupervisor.getName());
        assertEquals(SupervisorCategory.ETEP, createdSupervisor.getSupervisorCategory());
        assertEquals("CS", createdSupervisor.getDepartment().getName());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateSupervisor() {
        // Update a supervisor
        Supervisor updateSupervisor = new Supervisor("UpdatedMark", "White", "DR. H", "123456789", "mw@email.com", SupervisorCategory.PHD, department);
        Supervisor updatedSupervisor = supervisorService.update(7002, updateSupervisor);

        assertNotNull(updatedSupervisor);
        assertEquals(7002, updatedSupervisor.getId());
        assertEquals("UpdatedMark", updatedSupervisor.getName());
        assertEquals(SupervisorCategory.PHD, updatedSupervisor.getSupervisorCategory());
        assertEquals("mw@email.com", updatedSupervisor.getEmail());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testDeleteSupervisor() {
        supervisorService.delete(7002);

        Supervisor deletedSupervisor = supervisorRepository.findById(7002);
        List<Supervisor> foundSupervisors = supervisorRepository.listAll();

        assertNull(deletedSupervisor);
        assertEquals(5, foundSupervisors.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneSupervisor() {
        Supervisor foundSupervisor = supervisorService.findOne(7001);
        assertNotNull(foundSupervisor);
        assertEquals(7001, foundSupervisor.getId());
        assertEquals(SupervisorCategory.EDIP, foundSupervisor.getSupervisorCategory());
        assertEquals("John", foundSupervisor.getName());
        assertEquals("CS", foundSupervisor.getDepartment().getName());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateSupervisorFailed() {
        // Update a supervisor
        Supervisor updateSupervisor = new Supervisor("UpdatedMark", "White", "mw@email.com", "123456789", "DR. H", SupervisorCategory.PHD, department);
        assertThrows(NotFoundException.class, () ->
                supervisorService.update(999, updateSupervisor));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindSupervisorFailed() {
        assertThrows(NotFoundException.class, () ->
                supervisorService.findOne(999));
    }
}

