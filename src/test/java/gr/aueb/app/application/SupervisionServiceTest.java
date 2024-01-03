package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.persistence.SupervisionRepository;
import gr.aueb.app.representation.*;
import gr.aueb.app.persistence.SupervisorRepository;

import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SupervisionServiceTest {

    @Inject
    SupervisionService supervisionService;

    @Inject
    SupervisionMapper supervisionMapper;

    @Inject
    ExaminationMapper examinationMapper;

    @Inject
    SupervisorMapper supervisorMapper;

    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllSupervisions() {
        List<Supervision> foundSupervisions = supervisionService.findAll();
        assertNotNull(foundSupervisions);
        assertEquals(2, foundSupervisions.size());
    }


    @Test
    @TestTransaction
    @Transactional
    public void testCreateSupervision() {
        SupervisionRepresentation representation = new SupervisionRepresentation();
        Examination examination = examinationRepository.findById(8001);
        ExaminationRepresentation examinationRepresentation = examinationMapper.toRepresentation(examination);
        Supervisor supervisor = supervisorRepository.findById(7002);
        SupervisorRepresentation supervisorRepresentation = supervisorMapper.toRepresentation(supervisor);

        representation.examination = examinationRepresentation;
        representation.supervisor = supervisorRepresentation;

        Supervision createdSupervision = supervisionService.create(representation);
        List<Supervision> foundSupervisions = supervisionService.findAll();

        assertNotNull(createdSupervision);
        assertNotNull(createdSupervision.getId());
        assertEquals(8001, createdSupervision.getExamination().getId());
        assertEquals("Intro to Programming", createdSupervision.getExamination().getSubject().getTitle());
        assertTrue(createdSupervision.getIsPresent());
        assertFalse(createdSupervision.getIsLead());
        assertEquals("Mark", createdSupervision.getSupervisor().getName());
        assertEquals(3, foundSupervisions.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateSupervision() {
        // Update a supervisor
        SupervisionRepresentation updatedRepresentation = new SupervisionRepresentation();
        Supervision supervision = supervisionRepository.findById(10002);
        SupervisionRepresentation oldRepresentation = supervisionMapper.toRepresentation(supervision);
        Examination examination = examinationRepository.findById(8004);
        ExaminationRepresentation examinationRepresentation = examinationMapper.toRepresentation(examination);

        updatedRepresentation.supervisor = oldRepresentation.supervisor;
        updatedRepresentation.examination = examinationRepresentation;
        Supervision updatedSupervision = supervisionService.update(10002, updatedRepresentation);

        assertNotNull(updatedSupervision);
        assertEquals(10002, updatedSupervision.getId());
        assertEquals(8004, updatedSupervision.getExamination().getId());
        assertFalse(updatedSupervision.getIsLead());
        assertEquals("George", updatedSupervision.getSupervisor().getName());
        assertEquals(LocalDate.of(2024, 06, 15), updatedSupervision.getExamination().getStartDate());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testDeleteSupervision() {
        supervisionService.delete(10001);

        Supervision deletedSupervision = supervisionRepository.findById(10001);
        List<Supervision> foundSupervisions = supervisionService.findAll();

        assertNull(deletedSupervision);
        assertEquals(1, foundSupervisions.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneSupervision() {
        Supervision foundSupervision = supervisionService.findOne(10002);
        assertNotNull(foundSupervision);
        assertEquals(10002, foundSupervision.getId());
        assertFalse(foundSupervision.getIsLead());
        assertEquals(8003, foundSupervision.getExamination().getId());
        assertEquals(7004, foundSupervision.getSupervisor().getId());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testChangePresentStatus() {
        Supervision updatedSupervision = supervisionService.changePresentStatus(10001, false);
        assertNotNull(updatedSupervision);
        assertEquals(10001, updatedSupervision.getId());
        assertFalse(updatedSupervision.getIsPresent());

        assertThrows(NotFoundException.class, () -> {
            supervisionService.changePresentStatus(9999, false);
        });
    }

    @Test
    @TestTransaction
    @Transactional
    public void testChangeLeadStatus() {
        Supervision updatedSupervision = supervisionService.changeLeadStatus(10002, true);
        assertNotNull(updatedSupervision);
        assertEquals(10002, updatedSupervision.getId());
        assertTrue(updatedSupervision.getIsLead());

        assertThrows(NotFoundException.class, () -> {
            supervisionService.changeLeadStatus(9999, true);
        });
    }
}
