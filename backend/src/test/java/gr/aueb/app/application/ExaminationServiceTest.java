package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.ClassroomRepository;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import gr.aueb.app.persistence.CourseRepository;
import gr.aueb.app.representation.*;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ExaminationServiceTest {

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    ClassroomRepository classroomRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    ExaminationService examinationService;

    @Inject
    ExaminationPeriodMapper examinationPeriodMapper;

    @Inject
    ClassroomMapper classroomMapper;

    @Inject
    CourseMapper courseMapper;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllExaminations() {
        List<Examination> foundExaminations = examinationService.findAll();
        assertNotNull(foundExaminations);
        assertEquals(7, foundExaminations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testCreateExamination() {
        ExaminationRepresentation representation = new ExaminationRepresentation();

        ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(5003);
        ExaminationPeriodRepresentation examinationPeriodRepresentation = examinationPeriodMapper.toRepresentation(examinationPeriod);

        Classroom classroom = classroomRepository.findById(2001);
        ClassroomRepresentation classroomRepresentation = classroomMapper.toRepresentation(classroom);

        Course course = courseRepository.findById(4001);
        CourseRepresentation courseRepresentation = courseMapper.toRepresentation(course);

        representation.date = "2024-09-05";
        representation.startTime = "11:00";
        representation.endTime = "13:00";
        representation.course = courseRepresentation;
        representation.examinationPeriod = examinationPeriodRepresentation;
        representation.classrooms.add(classroomRepresentation);

        Examination createdExamination = examinationService.create(representation);
        List<Examination> foundExaminations = examinationService.findAll();

        assertEquals(8, foundExaminations.size());
        assertNotNull(createdExamination);
        assertNotNull(createdExamination.getId());
        assertEquals(LocalDate.of(2024, 9, 5), createdExamination.getDate());
        assertEquals("CS105", createdExamination.getCourse().getCourseCode());
        assertEquals(Period.SEPTEMBER, createdExamination.getExaminationPeriod().getPeriod());
        assertEquals(1, createdExamination.getClassrooms().size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneExamination() {
        Examination foundExamination = examinationService.findOne(8002);
        assertNotNull(foundExamination);
        assertEquals(8002, foundExamination.getId());
        assertEquals(8, foundExamination.getRequiredSupervisors());
        assertEquals(4002, foundExamination.getCourse().getId());
        assertEquals(5001, foundExamination.getExaminationPeriod().getId());
//        assertEquals(90, foundExamination.getTotalDeclaration());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllInSamePeriod() {
        List<Examination> foundExaminations = examinationService.findAllInSamePeriod(5003);
        assertEquals(2, foundExaminations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testAddSupervision() {
        Supervision supervision1 = examinationService.addSupervision(8001, 7002);
        Supervision supervision2 = examinationService.addSupervision(8004, 7003);

        assertNotNull(supervision1);
        assertEquals(2, supervision1.getExamination().getSupervisions().size());
        assertNull(supervision2);
    }

    @Test
    @TestTransaction
    @Transactional
    public void testRemoveSupervision() {
        examinationService.removeSupervision(8001, 10001);
        Examination foundExamination = examinationService.findOne(8001);
        assertEquals(0, foundExamination.getSupervisions().size());
    }
}
