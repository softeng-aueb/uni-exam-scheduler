package gr.aueb.app.application;

import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorCategory;
import gr.aueb.app.representation.DepartmentRepresentation;
import gr.aueb.app.representation.SupervisorRepresentation;
import gr.aueb.app.persistence.SupervisorRepository;

import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SupervisorServiceTest {

    @Inject
    SupervisorService supervisorService;

    @Inject
    SupervisorRepository supervisorRepository;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllSupervisors() {
        List<Supervisor> foundSupervisors = supervisorService.findAll();
        assertNotNull(foundSupervisors);
        assertEquals(6, foundSupervisors.size());
    }


    @Test
    @TestTransaction
    @Transactional
    public void testCreateSupervisor() {
        SupervisorRepresentation representation = new SupervisorRepresentation();
        DepartmentRepresentation departmentRepresentation = new DepartmentRepresentation();
        representation.name = "Super";
        representation.surname = "Visor";
        representation.supervisor = "supervisor";
        representation.email = "supervisor@mail.com";
        representation.telephone = "1234";
        representation.supervisorCategory = SupervisorCategory.ETEP;
        departmentRepresentation.id = 3002;
        departmentRepresentation.name = "Business";
        representation.department = departmentRepresentation;

        Supervisor createdSupervisor = supervisorService.create(representation);
        List<Supervisor> foundSupervisors = supervisorService.findAll();

        assertNotNull(createdSupervisor);
        assertNotNull(createdSupervisor.getId());
        assertEquals("Super", createdSupervisor.getName());
        assertEquals(SupervisorCategory.ETEP, createdSupervisor.getSupervisorCategory());
        assertEquals(3002, createdSupervisor.getDepartment().getId());
        assertEquals("Business", createdSupervisor.getDepartment().getName());
        assertEquals(7, foundSupervisors.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateSupervisor() {
        // Update a supervisor
        SupervisorRepresentation updatedRepresentation = new SupervisorRepresentation();
        // Set properties of updatedRepresentation as needed
        updatedRepresentation.name = "UpdatedMark";
        updatedRepresentation.surname = "White";
        updatedRepresentation.email = "mw@email.com";
        updatedRepresentation.telephone = "123456789";
        updatedRepresentation.supervisor = "DR. H";
        updatedRepresentation.supervisorCategory = SupervisorCategory.PHD;
        Supervisor updatedSupervisor = supervisorService.update(7002, updatedRepresentation);

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
        List<Supervisor> foundSupervisors = supervisorService.findAll();

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
}

