package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentParticipationTest {

    private DepartmentParticipation departmentParticipation;
    @BeforeEach
    void setup() {
        departmentParticipation = new DepartmentParticipation(150, true, new Course(), new Department(), new ExaminationPeriod());
    }

    @Test
    void testCreateDepartmentParticipation() {
        assertNotNull(departmentParticipation);
        assertEquals(150, departmentParticipation.getDeclaration());
        assertTrue(departmentParticipation.getIsLeadDepartment());
        assertNotNull(departmentParticipation.getCourse());
        assertNotNull(departmentParticipation.getDepartment());
        assertNotNull(departmentParticipation.getExaminationPeriod());
    }

    @Test
    void testSettersAndGetters() {
        Course newCourse = new Course();
        ExaminationPeriod newExaminationPeriod = new ExaminationPeriod();
        Department newDepartment = new Department();
        departmentParticipation.setId(3);
        departmentParticipation.setDeclaration(200);
        departmentParticipation.setAttendance(170);
        departmentParticipation.setIsLeadDepartment(false);
        departmentParticipation.setCourse(newCourse);
        departmentParticipation.setDepartment(newDepartment);
        departmentParticipation.setExaminationPeriod(newExaminationPeriod);

        assertEquals(3, departmentParticipation.getId());
        assertEquals(200, departmentParticipation.getDeclaration());
        assertEquals(170, departmentParticipation.getAttendance());
        assertEquals(newCourse, departmentParticipation.getCourse());
        assertEquals(newDepartment, departmentParticipation.getDepartment());
        assertEquals(newExaminationPeriod, departmentParticipation.getExaminationPeriod());
    }
}
