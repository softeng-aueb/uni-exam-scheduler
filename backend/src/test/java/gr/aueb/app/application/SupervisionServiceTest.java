package gr.aueb.app.application;

import gr.aueb.app.domain.Supervision;

import gr.aueb.app.persistence.SupervisionRepository;
import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SupervisionServiceTest {

    @Inject
    SupervisionService supervisionService;

    @Inject
    SupervisionRepository supervisionRepository;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllSupervisions() {
        List<Supervision> foundSupervisions = supervisionService.findAll();
        assertNotNull(foundSupervisions);
        assertEquals(3, foundSupervisions.size());
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

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllInSameSupervisorAndDay() {
        List<Supervision> foundSupervisions = supervisionService.findAllInSameSupervisorAndDay(7004, LocalDate.of(2024, 6, 10));
        assertNotNull(foundSupervisions);
        assertEquals(2, foundSupervisions.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneSupervisionFailed() {
        assertThrows(NotFoundException.class, ()->
                supervisionService.findOne(999));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testDeleteAllInSamePeriod() {
        supervisionService.deleteAllInSamePeriod(5001);
        List<Supervision> supervisions = supervisionRepository.listAll();

        assertEquals(1, supervisions.size());
    }
}
