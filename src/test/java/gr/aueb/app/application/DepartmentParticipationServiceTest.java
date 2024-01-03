package gr.aueb.app.application;

import gr.aueb.app.domain.Department;
import gr.aueb.app.domain.DepartmentParticipation;
import gr.aueb.app.domain.Examination;
import gr.aueb.app.persistence.DepartmentParticipationRepository;
import gr.aueb.app.persistence.DepartmentRepository;
import gr.aueb.app.persistence.ExaminationRepository;
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
    DepartmentRepository departmentRepository;

    @Inject
    DepartmentMapper departmentMapper;

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    ExaminationMapper examinationMapper;

    @Test
    @TestTransaction
    @Transactional
    public void testCreateDepartmentParticipation() {
        DepartmentParticipationRepresentation representation = new DepartmentParticipationRepresentation();

        Department department = departmentRepository.findById(3005);
        DepartmentRepresentation departmentRepresentation = departmentMapper.toRepresentation(department);

        Examination examination = examinationRepository.findById(8006);
        ExaminationRepresentation examinationRepresentation = examinationMapper.toRepresentation(examination);

        representation.isLeadDepartment = true;
        representation.declaration = 160;
        representation.department = departmentRepresentation;
        representation.examination = examinationRepresentation;

        DepartmentParticipation createdDepartmentParticipation = departmentParticipationService.create(representation);
        List<DepartmentParticipation> departmentParticipations = departmentParticipationRepository.listAll();

        assertNotNull(createdDepartmentParticipation);
        assertNotNull(createdDepartmentParticipation.getId());
        assertEquals(160, createdDepartmentParticipation.getDeclaration());
        assertTrue(createdDepartmentParticipation.getIsLeadDepartment());
        assertEquals("Marketing", createdDepartmentParticipation.getDepartment().getName());
        assertEquals(LocalDate.of(2024, 9, 7), createdDepartmentParticipation.getExamination().getStartDate());
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
