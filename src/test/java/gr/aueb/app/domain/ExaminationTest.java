package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminationTest {

    private Examination examination;
    private Subject subject;
    private ExaminationPeriod examinationPeriod;

    @BeforeEach
    void setup() {
        subject = new Subject("Algorithms", "CS101");
        AcademicYear academicYear = new AcademicYear("2023-2024", true, new AcademicYear());
        examinationPeriod = new ExaminationPeriod(Semester.THIRD, LocalDate.now(), Period.WINTER, academicYear);
        examination = new Examination(LocalDate.now(), LocalDate.of(2023, 12, 20), 10, new HashSet<>(), subject, new HashSet<>(), examinationPeriod);
    }

    @Test
    void testCreateExamination() {
        assertNotNull(examination);
        assertEquals(LocalDate.now(), examination.getStart_date());
        assertEquals(LocalDate.of(2023, 12, 20), examination.getEnd_date());
        assertEquals(10, examination.getRequired_supervisors());
        assertNotNull(examination.getDepartmentParticipations());
        assertEquals(subject, examination.getSubject());
        assertNotNull(examination.getClassrooms());
        assertEquals(examinationPeriod, examination.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Subject newSubject = new Subject();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Set<Classroom> classroomSet = new HashSet<>();
        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
        Set<Supervision> supervisionSet = new HashSet<>();
        examination.setId(10);
        examination.setStart_date(LocalDate.of(2023, 12, 10));
        examination.setEnd_date(LocalDate.of(2023, 12, 30));
        examination.setRequired_supervisors(8);
        examination.setSubject(newSubject);
        examination.setExaminationPeriod(newExaminationPeriod);
        examination.setClassrooms(classroomSet);
        examination.setDepartmentParticipations(departmentParticipationSet);
        examination.setSupervisions(supervisionSet);

        assertEquals(10, examination.getId());
        assertEquals(LocalDate.of(2023, 12, 10), examination.getStart_date());
        assertEquals(LocalDate.of(2023, 12, 30), examination.getEnd_date());
        assertEquals(8, examination.getRequired_supervisors());
        assertEquals(newSubject, examination.getSubject());
        assertEquals(newExaminationPeriod, examination.getExaminationPeriod());
        assertEquals(classroomSet, examination.getClassrooms());
        assertEquals(departmentParticipationSet, examination.getDepartmentParticipations());
        assertEquals(supervisionSet, examination.getSupervisions());
    }
}
