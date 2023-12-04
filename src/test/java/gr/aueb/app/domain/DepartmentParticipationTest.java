package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentParticipationTest {

    private DepartmentParticipation departmentParticipation;
    @BeforeEach
    void setup() {
        departmentParticipation = new DepartmentParticipation(150, true, new Examination(), new Department());
    }

    @Test
    void testCreateDepartmentParticipation() {
        assertNotNull(departmentParticipation);
        assertEquals(150, departmentParticipation.getDeclaration());
        assertTrue(departmentParticipation.getIsLeadDepartment());
        assertNotNull(departmentParticipation.getExamination());
        assertNotNull(departmentParticipation.getDepartment());
    }

    @Test
    void testSettersAndGetters() {
        Examination newExamination = new Examination();
        Department newDepartment = new Department();
        departmentParticipation.setId(3);
        departmentParticipation.setDeclaration(200);
        departmentParticipation.setAttendance(170);
        departmentParticipation.setIsLeadDepartment(false);
        departmentParticipation.setDepartment(newDepartment);
        departmentParticipation.setExamination(newExamination);

        assertEquals(3, departmentParticipation.getId());
        assertEquals(200, departmentParticipation.getDeclaration());
        assertEquals(170, departmentParticipation.getAttendance());
        assertEquals(newExamination, departmentParticipation.getExamination());
        assertEquals(newDepartment, departmentParticipation.getDepartment());
    }
}
