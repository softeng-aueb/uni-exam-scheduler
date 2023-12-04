package gr.aueb.app.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AcademicYearTest {

    @Test
    void testCreateAcademicYear() {
        AcademicYear academicYear = new AcademicYear("2023-2024", true, null);

        assertEquals("2023-2024", academicYear.getName());
        assertTrue(academicYear.getActive());
        assertNull(academicYear.getPreviousYear());
        assertTrue(academicYear.getExaminationPeriods().isEmpty());
    }

    @Test
    void testSettersAndGetters() {
        AcademicYear academicYear = new AcademicYear();
        Set<ExaminationPeriod> examinationPeriods = new HashSet<>();
        ExaminationPeriod examinationPeriod = new ExaminationPeriod();
        examinationPeriods.add(examinationPeriod);
        AcademicYear academicYear2 = new AcademicYear("2023-2024", true, null);

        academicYear.setId(1);
        academicYear.setName("2022-2023");
        academicYear.setActive(true);
        academicYear.setExaminationPeriods(examinationPeriods);
        academicYear2.setPreviousYear(academicYear);

        assertEquals(1, academicYear.getId());
        assertEquals("2022-2023", academicYear.getName());
        assertTrue(academicYear.getActive());
        assertTrue(academicYear.getExaminationPeriods().contains(examinationPeriod));
        assertEquals(academicYear, academicYear2.getPreviousYear());
    }

    @Test
    void testAddExaminationPeriod() {
        AcademicYear academicYear = new AcademicYear("2022-2023", true, null);
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(Semester.THIRD, LocalDate.now(), Period.WINTER, academicYear);

        academicYear.addExaminationPeriod(examinationPeriod);

        assertTrue(academicYear.getExaminationPeriods().contains(examinationPeriod));
        assertEquals(academicYear, examinationPeriod.getAcademicYear());
    }

    @Test
    void testRemoveExaminationPeriod() {
        AcademicYear academicYear = new AcademicYear("2022-2023", true, null);
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(Semester.THIRD, LocalDate.now(), Period.WINTER, academicYear);
        academicYear.getExaminationPeriods().add(examinationPeriod);

        academicYear.removeExaminationPeriod(examinationPeriod);

        assertFalse(academicYear.getExaminationPeriods().contains(examinationPeriod));
        assertNull(examinationPeriod.getAcademicYear());
    }
}