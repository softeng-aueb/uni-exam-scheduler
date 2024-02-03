package gr.aueb.app.application;


import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
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
public class DepartmentParticipationServiceTest {

    @Inject
    DepartmentParticipationService departmentParticipationService;

    @Inject
    DepartmentParticipationRepository departmentParticipationRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    CourseMapper courseMapper;

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    DepartmentMapper departmentMapper;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    ExaminationPeriodMapper examinationPeriodMapper;

    @Test
    @TestTransaction
    @Transactional
    public void testCreateDepartmentParticipation() {
        DepartmentParticipationRepresentation representation = new DepartmentParticipationRepresentation();

        Course course = courseRepository.findById(4002);
        CourseRepresentation courseRepresentation = courseMapper.toRepresentation(course);

        Department department = departmentRepository.findById(3005);
        DepartmentRepresentation departmentRepresentation = departmentMapper.toRepresentation(department);

        ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(5003);
        ExaminationPeriodRepresentation examinationPeriodRepresentation = examinationPeriodMapper.toRepresentation(examinationPeriod);

        representation.isLeadDepartment = true;
        representation.declaration = 160;
        representation.course = courseRepresentation;
        representation.department = departmentRepresentation;
        representation.examinationPeriod = examinationPeriodRepresentation;

        DepartmentParticipation createdDepartmentParticipation = departmentParticipationService.create(representation);
        List<DepartmentParticipation> departmentParticipations = departmentParticipationRepository.listAll();

        assertNotNull(createdDepartmentParticipation);
        assertNotNull(createdDepartmentParticipation.getId());
        assertEquals(160, createdDepartmentParticipation.getDeclaration());
        assertTrue(createdDepartmentParticipation.getIsLeadDepartment());
        assertEquals("AF101", createdDepartmentParticipation.getCourse().getCourseCode());
        assertEquals("Marketing", createdDepartmentParticipation.getDepartment().getName());
        assertEquals(LocalDate.of(2024, 9, 3), createdDepartmentParticipation.getExaminationPeriod().getStartDate());
        assertEquals(8, departmentParticipations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateAttendance() {
        DepartmentParticipation updatedDepartmentParticipation = departmentParticipationService.updateAttendance(9007, 200);

        assertEquals(9007, updatedDepartmentParticipation.getId());
        assertEquals(200, updatedDepartmentParticipation.getAttendance());
    }
}
