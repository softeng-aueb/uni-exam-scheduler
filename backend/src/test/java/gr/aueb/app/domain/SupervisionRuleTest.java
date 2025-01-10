package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SupervisionRuleTest {

    private ExaminationPeriod examinationPeriod;
    private Department department;
    private SupervisionRule supervisionRule;

    @BeforeEach
    void setup() {
        department = new Department("CS");
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        supervisionRule = new SupervisionRule(3, SupervisorCategory.PHD, department, examinationPeriod);
    }

    @Test
    void testCreateSupervisionRule() {
        assertNotNull(supervisionRule);

        assertEquals(3, supervisionRule.getNumOfSupervisions());
        assertEquals(SupervisorCategory.PHD, supervisionRule.getSupervisorCategory());
        assertEquals("CS", supervisionRule.getDepartment().getName());
        assertEquals(Period.WINTER, supervisionRule.getExaminationPeriod().getPeriod());
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
