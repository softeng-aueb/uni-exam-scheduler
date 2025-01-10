package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminationPeriodTest {
    AcademicYear academicYear;
    @BeforeEach
    public void setup () {
        academicYear = new AcademicYear("2022-2023", null);
    }

    @Test
    public void testCreateExaminationPeriod() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);

        assertEquals(LocalDate.now(), examinationPeriod.getStartDate());
        assertEquals(Period.WINTER, examinationPeriod.getPeriod());
        assertEquals(academicYear, examinationPeriod.getAcademicYear());
    }

    @Test
    public void testSettersAndGetters() {
        ExaminationPeriod examinationPeriod = new ExaminationPeriod();

        examinationPeriod.setId(1);
        examinationPeriod.setStartDate(LocalDate.now());
        examinationPeriod.setPeriod(Period.SEPTEMBER);
        examinationPeriod.setAcademicYear(academicYear);

        assertEquals(1, examinationPeriod.getId());
        assertEquals(LocalDate.now(), examinationPeriod.getStartDate());
        assertEquals(Period.SEPTEMBER, examinationPeriod.getPeriod());
        assertEquals(academicYear, examinationPeriod.getAcademicYear());
    }
}
