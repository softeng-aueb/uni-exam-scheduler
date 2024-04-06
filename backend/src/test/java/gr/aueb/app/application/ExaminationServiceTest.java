package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.ExaminationRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ExaminationServiceTest {
    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    ExaminationService examinationService;

    private Course course;
    private Department department;
    private Set<Classroom> classrooms = new HashSet<>();
    private ExaminationPeriod examinationPeriod;

    @BeforeEach
    public void setup() {
        department = new Department("Accounting");
        course = new Course("Advanced Accounting", "AF110", department);
        classrooms.add(new Classroom("D9", "Second", 1, 200, 120, 70, 5));
        classrooms.add(new Classroom("A5", "Central", 1, 200, 120, 70, 5));
        examinationPeriod = new ExaminationPeriod(LocalDate.of(2024, 9, 3), Period.SEPTEMBER, null);
    }

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
        Examination newExamination = new Examination(
                LocalDate.now(),
                LocalTime.of(13, 0),
                LocalTime.of(14, 45),
                course,
                classrooms,
                examinationPeriod
        );

        Examination createdExamination = examinationService.create(newExamination);

        assertNotNull(createdExamination);
        assertNotNull(createdExamination.getId());
        assertEquals(LocalDate.now(), createdExamination.getDate());
        assertEquals(14, createdExamination.getEndTime().getHour());
        assertEquals("AF110", createdExamination.getCourse().getCourseCode());
        assertEquals(Period.SEPTEMBER, createdExamination.getExaminationPeriod().getPeriod());
        assertEquals(2, createdExamination.getClassrooms().size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneExamination() {
        Examination foundExamination = examinationService.findOne(8002);
        assertNotNull(foundExamination);
        assertEquals(8002, foundExamination.getId());
        assertEquals(4002, foundExamination.getCourse().getId());
        assertEquals(5001, foundExamination.getExaminationPeriod().getId());
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

        assertNotNull(supervision1);
        assertEquals(8001, supervision1.getExamination().getId());
        assertEquals(7002, supervision1.getSupervisor().getId());
        assertEquals(2, supervision1.getExamination().getSupervisions().size());
        assertThrows(BadRequestException.class, () ->
                examinationService.addSupervision(8004, 7003));
        assertThrows(NotFoundException.class, () ->
                examinationService.addSupervision(999, 999));
        assertThrows(NotFoundException.class, () ->
                examinationService.addSupervision(8001, 999));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testRemoveSupervision() {
        examinationService.removeSupervision(8001, 10001);
        Examination foundExamination = examinationRepository.findById(8001);
        assertEquals(0, foundExamination.getSupervisions().size());
        assertThrows(NotFoundException.class, () ->
                examinationService.removeSupervision(999, 999));
        assertThrows(NotFoundException.class, () ->
                examinationService.removeSupervision(8001, 999));
        assertThrows(BadRequestException.class, () ->
                examinationService.removeSupervision(8001, 10002));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindOneExaminationFailed() {
        assertThrows(NotFoundException.class, () ->
                examinationService.findOne(999));
    }
}
