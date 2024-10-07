package gr.aueb.app.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AcademicYearTest {

    @Test
    void testCreateAcademicYear() {
        AcademicYear academicYear = new AcademicYear("2023-2024", null);

        assertEquals("2023-2024", academicYear.getName());
        assertFalse(academicYear.getIsActive());
        assertNull(academicYear.getPreviousYear());
    }

    @Test
    void testSettersAndGetters() {
        AcademicYear academicYear = new AcademicYear();
        AcademicYear academicYear2 = new AcademicYear("2023-2024", null);

        academicYear.setId(1);
        academicYear.setName("2022-2023");
        academicYear.setIsActive(true);
        academicYear2.setPreviousYear(academicYear);

        assertEquals(1, academicYear.getId());
        assertEquals("2022-2023", academicYear.getName());
        assertTrue(academicYear.getIsActive());
        assertEquals(academicYear, academicYear2.getPreviousYear());
    }
}