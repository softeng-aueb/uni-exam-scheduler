package gr.aueb.app.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class SupervisorPreferenceTest {

    private Supervisor supervisor;
    private ExaminationPeriod examinationPeriod;
    private SupervisorPreference supervisorPreference;

    @BeforeEach
    void setup() {
        Department department = new Department("CS");
        supervisor = new Supervisor("John", "Doe", "O'Reily", "123456789", "john.doe@example.com", SupervisorCategory.EDIP, department);
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        Set<LocalDate> excludeDates = new HashSet<>();
        excludeDates.add(LocalDate.now());
        supervisorPreference = new SupervisorPreference(LocalTime.of(11, 0), LocalTime.of(13, 0), excludeDates, supervisor, examinationPeriod);
    }

    @Test
    void testCreateSupervisorPreference() {
        assertNotNull(supervisorPreference);
        assertEquals(LocalTime.of(11, 0), supervisorPreference.getStartTime());
        assertEquals(LocalTime.of(13, 0), supervisorPreference.getEndTime());
        assertEquals(1, supervisorPreference.getExcludeDates().size());
        assertEquals("John", supervisorPreference.getSupervisor().getName());
        assertEquals(Period.WINTER, supervisorPreference.getExaminationPeriod().getPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Supervisor newSupervisor = new Supervisor();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Set<LocalDate> newExcludeDates = new HashSet<>();
        supervisorPreference.setId(5);
        supervisorPreference.setSupervisor(newSupervisor);
        supervisorPreference.setExaminationPeriod(newExaminationPeriod);
        supervisorPreference.setExcludeDates(newExcludeDates);
        supervisorPreference.setStartTime(LocalTime.of(10, 0));
        supervisorPreference.setEndTime(LocalTime.of(12, 0));

        assertEquals(5, supervisorPreference.getId());
        assertEquals(newSupervisor, supervisorPreference.getSupervisor());
        assertEquals(newExaminationPeriod, supervisorPreference.getExaminationPeriod());
        assertEquals(0, supervisorPreference.getExcludeDates().size());
        assertEquals(LocalTime.of(10, 0), supervisorPreference.getStartTime());
        assertEquals(LocalTime.of(12, 0), supervisorPreference.getEndTime());
    }
}
