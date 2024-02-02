package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminationTest {

    private Examination examination;
    private Course course;
    private ExaminationPeriod examinationPeriod;

    @BeforeEach
    void setup() {
        course = new Course("Algorithms", "CS101");
        AcademicYear academicYear = new AcademicYear("2023-2024", true, new AcademicYear());
        examinationPeriod = new ExaminationPeriod(Semester.THIRD, LocalDate.now(), Period.WINTER, academicYear);
        examination = new Examination(LocalDate.now(), LocalDate.of(2023, 12, 20), course, new HashSet<>(), examinationPeriod);
    }

    @Test
    void testCreateExamination() {
        assertNotNull(examination);
        assertEquals(LocalDate.now(), examination.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 20), examination.getEndDate());
        assertNotNull(examination.getDepartmentParticipations());
        assertEquals(course, examination.getCourse());
        assertNotNull(examination.getClassrooms());
        assertEquals(examinationPeriod, examination.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Course newCourse = new Course();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Set<Classroom> classroomSet = new HashSet<>();
        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
        Set<Supervision> supervisionSet = new HashSet<>();
        examination.setId(10);
        examination.setStartDate(LocalDate.of(2023, 12, 10));
        examination.setEndDate(LocalDate.of(2023, 12, 30));
        examination.setRequiredSupervisors(8);
        examination.setSubject(newCourse);
        examination.setExaminationPeriod(newExaminationPeriod);
        examination.setClassrooms(classroomSet);
        examination.setDepartmentParticipations(departmentParticipationSet);
        examination.setSupervisions(supervisionSet);

        assertEquals(10, examination.getId());
        assertEquals(LocalDate.of(2023, 12, 10), examination.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 30), examination.getEndDate());
        assertEquals(8, examination.getRequiredSupervisors());
        assertEquals(newCourse, examination.getCourse());
        assertEquals(newExaminationPeriod, examination.getExaminationPeriod());
        assertEquals(classroomSet, examination.getClassrooms());
        assertEquals(departmentParticipationSet, examination.getDepartmentParticipations());
        assertEquals(supervisionSet, examination.getSupervisions());
    }

    @Test
    void testAddSupervision() {
        Supervision newSupervision = new Supervision();
        examination.addSupervision(newSupervision);

        assertTrue(examination.getSupervisions().contains(newSupervision));
        assertEquals(examination, newSupervision.getExamination());
    }

    @Test
    void testRemoveSupervision() {
        Supervision newSupervision = new Supervision();
        examination.getSupervisions().add(newSupervision);

        examination.removeSupervision(newSupervision);

        assertTrue(examination.getSupervisions().isEmpty());
        assertNull(newSupervision.getExamination());
    }

    @Test
    void testAddDepartmentParticipation() {
        DepartmentParticipation newDepartmentParticipation = new DepartmentParticipation();
        examination.addDepartmentParticipation(newDepartmentParticipation);

        assertTrue(examination.getDepartmentParticipations().contains(newDepartmentParticipation));
        assertEquals(examination, newDepartmentParticipation.getExamination());
    }

    @Test
    void testRemoveDepartmentParticipation() {
        DepartmentParticipation newDepartmentParticipation = new DepartmentParticipation();
        examination.getDepartmentParticipations().add(newDepartmentParticipation);

        examination.removeDepartmentParticipation(newDepartmentParticipation);

        assertTrue(examination.getDepartmentParticipations().isEmpty());
        assertNull(newDepartmentParticipation.getExamination());
    }

    @Test
    void testGetTotalDeclaration() {
        Department newDepartment1 = new Department("CS");
        Department newDepartment2 = new Department("MATH");
        DepartmentParticipation newDepartmentParticipation1 = new DepartmentParticipation(150, true, examination, newDepartment1);
        DepartmentParticipation newDepartmentParticipation2 = new DepartmentParticipation(80, false, examination, newDepartment2);
        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
        departmentParticipationSet.add(newDepartmentParticipation1);
        departmentParticipationSet.add(newDepartmentParticipation2);

        examination.setDepartmentParticipations(departmentParticipationSet);

        assertEquals(230, examination.getTotalDeclaration());
    }

    @Test
    void testGetTotalAttendance() {
        Department newDepartment1 = new Department("CS");
        Department newDepartment2 = new Department("MATH");
        DepartmentParticipation newDepartmentParticipation1 = new DepartmentParticipation(150, true, examination, newDepartment1);
        DepartmentParticipation newDepartmentParticipation2 = new DepartmentParticipation(80, false, examination, newDepartment2);
        newDepartmentParticipation1.setAttendance(100);
        newDepartmentParticipation2.setAttendance(67);
        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
        departmentParticipationSet.add(newDepartmentParticipation1);
        departmentParticipationSet.add(newDepartmentParticipation2);

        examination.setDepartmentParticipations(departmentParticipationSet);

        assertEquals(167, examination.getTotalAttendance());
    }
}
