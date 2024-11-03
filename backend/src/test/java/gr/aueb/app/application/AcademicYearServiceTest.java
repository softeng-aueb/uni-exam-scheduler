package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testCreate() {
        AcademicYear newAcademicYear = new AcademicYear("2024-2025", null);
        AcademicYear createdAcademicYear = academicYearService.create(newAcademicYear);

       assertNotNull(createdAcademicYear.getId());
       assertEquals("2024-2025", createdAcademicYear.getName());
       assertEquals(false, createdAcademicYear.getIsActive());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindActive() {
        AcademicYear activeAcademicYear = academicYearService.findActive();

        assertEquals(1002, activeAcademicYear.getId());
        assertEquals("2022-2023", activeAcademicYear.getName());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testSetActive() {
        AcademicYear oldActive= academicYearService.findActive();
        academicYearService.setActive(1003);

        assertEquals(1003, academicYearService.findActive().getId());
        assertFalse(oldActive.getIsActive());
    }
}
