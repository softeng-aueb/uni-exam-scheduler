package gr.aueb.app.application;

import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.domain.Period;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ExaminationPeriodServiceTest {

    @Inject
    ExaminationPeriodService examinationPeriodService;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<ExaminationPeriod> foundExaminations = examinationPeriodService.findAll();

        assertEquals(4, foundExaminations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllInSameYear() {
        List<ExaminationPeriod> foundExaminations = examinationPeriodService.findAllInSameYear(1002);

        assertEquals(3, foundExaminations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testCreate() {
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod(LocalDate.of(2024, 6, 10), Period.SPRING, null);
        ExaminationPeriod createdExaminationPeriod = examinationPeriodService.create(newExaminationPeriod, 1003);

        assertNotNull(createdExaminationPeriod.getId());
        assertEquals(Period.SPRING, createdExaminationPeriod.getPeriod());
        assertEquals("2023-2024", createdExaminationPeriod.getAcademicYear().getName());
    }
}
