package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SupervisionRuleTest {
    @Test
    void testCreateSupervisionRule() {
        SupervisionRule supervisionRule = new SupervisionRule(3, SupervisorCategory.PHD, new Department(), new ExaminationPeriod());
        assertNotNull(supervisionRule);

        assertEquals(3, supervisionRule.getNumOfSupervisions());
        assertEquals(SupervisorCategory.PHD, supervisionRule.getSupervisorCategory());
        assertNotNull(supervisionRule.getDepartment());
        assertNotNull(supervisionRule.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        SupervisionRule supervisionRule = new SupervisionRule();

        supervisionRule.setId(5);
        supervisionRule.setNumOfSupervisions(5);
        supervisionRule.setSupervisorCategory(SupervisorCategory.EXTERNAL);
        Department newDepartment = new Department();
        supervisionRule.setDepartment(newDepartment);
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        supervisionRule.setExaminationPeriod(newExaminationPeriod);

        assertEquals(5, supervisionRule.getId());
        assertEquals(5, supervisionRule.getNumOfSupervisions());
        assertEquals(SupervisorCategory.EXTERNAL, supervisionRule.getSupervisorCategory());
        assertEquals(newDepartment, supervisionRule.getDepartment());
        assertEquals(newExaminationPeriod, supervisionRule.getExaminationPeriod());
    }
}
