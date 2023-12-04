package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExaminationPeriodTest {
    AcademicYear academicYear;
    @BeforeEach
    public void setup () {
        academicYear = new AcademicYear("2022-2023", true, null);
    }

    @Test
    public void testCreateExaminationPeriod() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(Semester.FIRST, LocalDate.now(), Period.WINTER, academicYear);

        assertEquals(Semester.FIRST, examinationPeriod.getSemester());
        assertEquals(LocalDate.now(), examinationPeriod.getStart_date());
        assertEquals(Period.WINTER, examinationPeriod.getPeriod());
        assertEquals(academicYear, examinationPeriod.getAcademicYear());
        assertTrue(examinationPeriod.getSupervisionRules().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod();
        Set<SupervisionRule> supervisionRuleSet = new HashSet<>();

        examinationPeriod.setId(1);
        examinationPeriod.setSemester(Semester.FOURTH);
        examinationPeriod.setStart_date(LocalDate.now());
        examinationPeriod.setPeriod(Period.SEPTEMBER);
        examinationPeriod.setAcademicYear(academicYear);
        examinationPeriod.setSupervisionRules(supervisionRuleSet);

        assertEquals(1, examinationPeriod.getId());
        assertEquals(Semester.FOURTH, examinationPeriod.getSemester());
        assertEquals(LocalDate.now(), examinationPeriod.getStart_date());
        assertEquals(Period.SEPTEMBER, examinationPeriod.getPeriod());
        assertEquals(academicYear, examinationPeriod.getAcademicYear());
        assertTrue(examinationPeriod.getSupervisionRules().isEmpty());
    }

    @Test
    public void testAddSupervisionRules() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(Semester.FIRST, LocalDate.now(), Period.WINTER, academicYear);
        SupervisionRule supervisionRule = new SupervisionRule(3, SupervisorCategory.EDIP, null, null);

        examinationPeriod.addSupervisionRule(supervisionRule);

        assertTrue(examinationPeriod.getSupervisionRules().contains(supervisionRule));
        assertEquals(examinationPeriod, supervisionRule.getExaminationPeriod());
    }

    @Test void testRemoveSupervisionRules() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(Semester.FIRST, LocalDate.now(), Period.WINTER, academicYear);
        SupervisionRule supervisionRule = new SupervisionRule(3, SupervisorCategory.EDIP, null, examinationPeriod);

        examinationPeriod.getSupervisionRules().add(supervisionRule);
        examinationPeriod.removeSupervisionRule(supervisionRule);

        assertFalse(examinationPeriod.getSupervisionRules().contains(supervisionRule));
        assertNull(supervisionRule.getExaminationPeriod());
    }
}
