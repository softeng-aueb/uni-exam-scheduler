package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminationTest {

    private Examination examination;
    private Course course;
    private ExaminationPeriod examinationPeriod;
    private Department department;

    @BeforeEach
    void setup() {
        department = new Department("CS");
        course = new Course("Algorithms", "CS101", department);
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        examination = new Examination(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0), course, new HashSet<>(), examinationPeriod);
    }

    @Test
    void testCreateExamination() {
        assertNotNull(examination);
        assertEquals(LocalDate.now(), examination.getDate());
        assertEquals(LocalTime.of(11, 0), examination.getStartTime());
        assertEquals(LocalTime.of(13, 0), examination.getEndTime());
        assertEquals(course, examination.getCourse());
        assertNotNull(examination.getClassrooms());
        assertEquals(examinationPeriod, examination.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Course newCourse = new Course();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Set<Classroom> classroomSet = new HashSet<>();
        Set<Supervision> supervisionSet = new HashSet<>();
        examination.setId(10);
        examination.setDate(LocalDate.of(2023, 12, 10));
        examination.setStartTime(LocalTime.of(16, 30, 0));
        examination.setEndTime(LocalTime.of(18, 0, 0));
        //examination.setRequiredSupervisors(8);
        examination.setCourse(newCourse);
        examination.setExaminationPeriod(newExaminationPeriod);
        examination.setClassrooms(classroomSet);
        examination.setSupervisions(supervisionSet);

        assertEquals(10, examination.getId());
        assertEquals(LocalDate.of(2023, 12, 10), examination.getDate());
        assertEquals(LocalTime.of(16, 30, 0), examination.getStartTime());
        assertEquals(LocalTime.of(18, 0, 0), examination.getEndTime());
        //assertEquals(8, examination.getRequiredSupervisors());
        assertEquals(newCourse, examination.getCourse());
        assertEquals(newExaminationPeriod, examination.getExaminationPeriod());
        assertEquals(classroomSet, examination.getClassrooms());
        assertEquals(supervisionSet, examination.getSupervisions());
    }

    @Test
    void testAddSupervision() {
        Supervisor newSupervisor = new Supervisor();
        List<Supervision> supervisions = new ArrayList<>();
        // not enough space
        Supervision newSupervision = examination.addSupervision(newSupervisor, supervisions);

        //examination.setRequiredSupervisors(2);
        // supervision added
        Supervision newSupervision1 = examination.addSupervision(newSupervisor, supervisions);
        // supervisor already in
        Supervision newSupervision2 = examination.addSupervision(newSupervisor, supervisions);
        // supervisions overlap
        Supervisor newSupervisor1 = new Supervisor();
        Examination newExamination = new Examination();
        newExamination.setStartTime(LocalTime.of(10, 0));
        newExamination.setEndTime(LocalTime.of(12, 0));
        Supervision overlapSupervision = new Supervision(newExamination);
        overlapSupervision.setSupervisor(newSupervisor1);
        supervisions.add(overlapSupervision);
        Supervision newSupervision3 = examination.addSupervision(newSupervisor1, supervisions);

        assertNull(newSupervision);
        assertTrue(examination.getSupervisions().contains(newSupervision1));
        assertNull(newSupervision2);
        assertNull(newSupervision3);
    }

    @Test
    void testRemoveSupervision() {
        Supervision newSupervision = new Supervision();
        examination.getSupervisions().add(newSupervision);

        examination.removeSupervision(newSupervision);

        assertTrue(examination.getSupervisions().isEmpty());
        assertNull(newSupervision.getExamination());
    }

//    @Test
//    void testGetTotalDeclaration() {
//        Department newDepartment1 = new Department("CS");
//        Department newDepartment2 = new Department("MATH");
//        DepartmentParticipation newDepartmentParticipation1 = new DepartmentParticipation(150, true, course, newDepartment1, examinationPeriod);
//        DepartmentParticipation newDepartmentParticipation2 = new DepartmentParticipation(80, false, course, newDepartment2, examinationPeriod);
//        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
//        departmentParticipationSet.add(newDepartmentParticipation1);
//        departmentParticipationSet.add(newDepartmentParticipation2);
//
//        examination.setDepartmentParticipations(departmentParticipationSet);
//
//        assertEquals(230, examination.getTotalDeclaration());
//    }
//
//    @Test
//    void testGetTotalAttendance() {
//        Department newDepartment1 = new Department("CS");
//        Department newDepartment2 = new Department("MATH");
//        DepartmentParticipation newDepartmentParticipation1 = new DepartmentParticipation(150, true, examination, newDepartment1);
//        DepartmentParticipation newDepartmentParticipation2 = new DepartmentParticipation(80, false, examination, newDepartment2);
//        newDepartmentParticipation1.setAttendance(100);
//        newDepartmentParticipation2.setAttendance(67);
//        Set<DepartmentParticipation> departmentParticipationSet = new HashSet<>();
//        departmentParticipationSet.add(newDepartmentParticipation1);
//        departmentParticipationSet.add(newDepartmentParticipation2);
//
//        examination.setDepartmentParticipations(departmentParticipationSet);
//
//        assertEquals(167, examination.getTotalAttendance());
//    }
}
