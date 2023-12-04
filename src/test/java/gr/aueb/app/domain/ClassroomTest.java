package gr.aueb.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClassroomTest {

    private Classroom classroom;

    @BeforeEach
    void setup() {
        classroom = new Classroom("E16", "Main", 5, 80, 50, 35, 3);
    }

    @Test
    void testCreateClassroom() {
        assertNotNull(classroom);
        assertEquals("E16", classroom.getName());
        assertEquals("Main", classroom.getBuilding());
        assertEquals(5, classroom.getFloor());
        assertEquals(80, classroom.getGeneral_capacity());
        assertEquals(50, classroom.getExam_capacity());
        assertEquals(35, classroom.getCovid_capacity());
        assertEquals(3, classroom.getMax_num_supervisors());
    }

    @Test
    void testSettersandGetters() {
        classroom.setId(16);
        classroom.setName("C3");
        classroom.setBuilding("Troias");
        classroom.setFloor(3);
        classroom.setGeneral_capacity(100);
        classroom.setExam_capacity(70);
        classroom.setCovid_capacity(45);
        classroom.setMax_num_supervisors(5);

        assertEquals(16, classroom.getId());
        assertEquals("C3", classroom.getName());
        assertEquals("Troias", classroom.getBuilding());
        assertEquals(3, classroom.getFloor());
        assertEquals(100, classroom.getGeneral_capacity());
        assertEquals(70, classroom.getExam_capacity());
        assertEquals(45, classroom.getCovid_capacity());
        assertEquals(5, classroom.getMax_num_supervisors());
    }
}
