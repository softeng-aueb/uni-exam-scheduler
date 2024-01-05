package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AcademicYearServiceTest {

    @Inject
    AcademicYearService academicYearService;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<AcademicYear> foundAcademicYears = academicYearService.findAll();

        assertEquals(3, foundAcademicYears.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindActive() {
        AcademicYear activeAcademicYear = academicYearService.findActive();

        assertEquals(1003, activeAcademicYear.getId());
        assertEquals("2023-2024", activeAcademicYear.getName());
    }
}
