package gr.aueb.app.domain;

import gr.aueb.app.persistence.CourseAttendanceRepository;
import gr.aueb.app.persistence.CourseDeclarationRepository;
import jakarta.inject.Inject;
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
    private CourseDeclaration declaration;
    private Classroom classroom;
    private Set<Classroom> classrooms = new HashSet<>();

    @BeforeEach
    void setup() {
        classroom = new Classroom("E16", "Main", 5, 80, 50, 35, 3);
        classrooms.add(classroom);
        department = new Department("CS");
        course = new Course("Algorithms", "CS101", department);
        AcademicYear academicYear = new AcademicYear("2023-2024", new AcademicYear());
        examinationPeriod = new ExaminationPeriod(LocalDate.now(), Period.WINTER, academicYear);
        examination = new Examination(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(13, 0), course, classrooms, examinationPeriod);
        declaration = new CourseDeclaration(100, course, academicYear);
    }

    @Test
    void testCreateExamination() {
        assertNotNull(examination);
        assertEquals(LocalDate.now(), examination.getDate());
        assertEquals(LocalTime.of(11, 0), examination.getStartTime());
        assertEquals(LocalTime.of(13, 0), examination.getEndTime());
        assertEquals(course, examination.getCourse());
        assertEquals(1, examination.getClassrooms().size());
        assertEquals(examinationPeriod, examination.getExaminationPeriod());
        assertEquals(3, examination.getMaxSupervisors());
    }

    @Test
    void testSettersAndGetters() {
        // transient values
        examination.setDeclaration(declaration);
        assertEquals(100, examination.getDeclaration());
        assertEquals(3, examination.getMaxSupervisors());
        examination.setEstimatedAttendance((double) 8/5);
        assertEquals(80, examination.getEstimatedAttendance());
        assertEquals(2, examination.getEstimatedSupervisors());

        Course newCourse = new Course();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Set<Classroom> classroomSet = new HashSet<>();
        Set<Supervision> supervisionSet = new HashSet<>();
        examination.setId(10);
        examination.setDate(LocalDate.of(2023, 12, 10));
        examination.setStartTime(LocalTime.of(16, 30, 0));
        examination.setEndTime(LocalTime.of(18, 0, 0));
        examination.setCourse(newCourse);
        examination.setExaminationPeriod(newExaminationPeriod);
        examination.setClassrooms(classroomSet);
        examination.setSupervisions(supervisionSet);

        assertEquals(10, examination.getId());
        assertEquals(LocalDate.of(2023, 12, 10), examination.getDate());
        assertEquals(LocalTime.of(16, 30, 0), examination.getStartTime());
        assertEquals(LocalTime.of(18, 0, 0), examination.getEndTime());
        assertEquals(newCourse, examination.getCourse());
        assertEquals(newExaminationPeriod, examination.getExaminationPeriod());
        assertEquals(classroomSet, examination.getClassrooms());
        assertEquals(supervisionSet, examination.getSupervisions());
    }

    @Test
    void testAddSupervision() {
        Supervisor supervisor = new Supervisor();
        List<Supervision> supervisorSupervisions = new ArrayList<>();
        // not enough space
        examination.removeClassroom(classroom);
        Supervision newSupervision = examination.addSupervision(supervisor, supervisorSupervisions);

        // supervision added
        classrooms.add(classroom);
        examination.setClassrooms(classrooms);
        Supervision newSupervision1 = examination.addSupervision(supervisor, supervisorSupervisions);
        // supervisor already in
        Supervision newSupervision2 = examination.addSupervision(supervisor, supervisorSupervisions);
        // supervisions overlap
        Supervisor overlapSupervisor = new Supervisor();
        Examination overlapExamination = new Examination();
        overlapExamination.setStartTime(LocalTime.of(10, 0));
        overlapExamination.setEndTime(LocalTime.of(12, 0));
        Supervision overlapSupervision = new Supervision(overlapExamination);
        overlapSupervision.setSupervisor(overlapSupervisor);
        supervisorSupervisions.add(overlapSupervision);
        Supervision newSupervision3 = examination.addSupervision(overlapSupervisor, supervisorSupervisions);

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
}
