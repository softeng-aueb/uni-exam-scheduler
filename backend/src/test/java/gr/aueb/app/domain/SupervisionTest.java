package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class SupervisionTest {
    private Supervision supervision;
    private Examination examination;
    private Course course;
    private ExaminationPeriod examinationPeriod;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("CS");
        course = new Course("Algorithms", "CS101", department);
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        examination = new Examination(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0), course, new HashSet<Classroom>(), examinationPeriod);
        supervision = new Supervision(examination);
    }

    @Test
    void testCreateSupervision() {
        assertNotNull(supervision);
        assertTrue(supervision.getIsPresent());
        assertFalse(supervision.getIsLead());
        assertEquals("Algorithms", supervision.getExamination().getCourse().getTitle());
        assertNull(supervision.getSupervisor());
    }

    @Test
    void testSettersAndGetters() {
        Examination examination = new Examination();
        Supervisor supervisor = new Supervisor();
        supervision.setSupervisor(supervisor);
        supervision.setExamination(examination);
        supervision.setId(1);
        supervision.setIsLead(true);
        supervision.setIsPresent(false);

        assertEquals(1, supervision.getId());
        assertEquals(supervisor, supervision.getSupervisor());
        assertEquals(examination, supervision.getExamination());
        assertTrue(supervision.getIsLead());
        assertFalse(supervision.getIsPresent());
        assertEquals("(1)", supervision.toString());
    }
}
