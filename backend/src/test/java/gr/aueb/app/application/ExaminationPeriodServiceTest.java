package gr.aueb.app.application;

import gr.aueb.app.domain.ExaminationPeriod;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ExaminationPeriodServiceTest {

    @Inject
    ExaminationPeriodService examinationPeriodService;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<ExaminationPeriod> foundExaminations = examinationPeriodService.findAll();

        assertEquals(3, foundExaminations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllInSameYear() {
        List<ExaminationPeriod> foundExaminations = examinationPeriodService.findAllInSameYear(1003);

        assertEquals(2, foundExaminations.size());
    }
}
