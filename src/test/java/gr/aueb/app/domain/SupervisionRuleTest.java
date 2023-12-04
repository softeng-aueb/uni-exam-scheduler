package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupervisionRuleTest {
    @Test
    void testCreateSupervisionRule() {
        SupervisionRule supervisionRule = new SupervisionRule(3, SupervisorCategory.PHD, new Department(), new ExaminationPeriod());
        assertNotNull(supervisionRule);

        assertEquals(3, supervisionRule.getNum_of_supervisions());
        assertEquals(SupervisorCategory.PHD, supervisionRule.getSupervisor_category());
        assertNotNull(supervisionRule.getDepartment());
        assertNotNull(supervisionRule.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        SupervisionRule supervisionRule = new SupervisionRule();

        supervisionRule.setId(5);
        supervisionRule.setNum_of_supervisions(5);
        supervisionRule.setSupervisor_category(SupervisorCategory.EXTERNAL);
        Department newDepartment = new Department();
        supervisionRule.setDepartment(newDepartment);
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        supervisionRule.setExaminationPeriod(newExaminationPeriod);

        assertEquals(5, supervisionRule.getId());
        assertEquals(5, supervisionRule.getNum_of_supervisions());
        assertEquals(SupervisorCategory.EXTERNAL, supervisionRule.getSupervisor_category());
        assertEquals(newDepartment, supervisionRule.getDepartment());
        assertEquals(newExaminationPeriod, supervisionRule.getExaminationPeriod());
    }
}
