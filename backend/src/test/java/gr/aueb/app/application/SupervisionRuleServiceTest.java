package gr.aueb.app.application;

import gr.aueb.app.domain.SupervisionRule;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorCategory;
import gr.aueb.app.persistence.SupervisorRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class SupervisionRuleServiceTest {

    @Inject
    SupervisorRepository supervisorRepository;

    @Test
    @TestTransaction
    @Transactional
    public void testfindSupervisionRule() {
        Supervisor foundSupervisor = supervisorRepository.findById(7001);
        SupervisionRule foundSupervisionRule = SupervisionRuleService.findSupervisionRule(5004, foundSupervisor);

        assertEquals(6, foundSupervisionRule.getNumOfSupervisions());
        assertEquals(SupervisorCategory.EDIP, foundSupervisionRule.getSupervisorCategory());
        assertEquals("CS", foundSupervisionRule.getDepartment().getName());
    }
}
