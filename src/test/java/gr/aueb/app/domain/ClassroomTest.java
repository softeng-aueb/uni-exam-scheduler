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
        assertEquals(80, classroom.getGeneralCapacity());
        assertEquals(50, classroom.getExamCapacity());
        assertEquals(35, classroom.getCovidCapacity());
        assertEquals(3, classroom.getMaxNumSupervisors());
    }

    @Test
    void testSettersandGetters() {
        classroom.setId(16);
        classroom.setName("C3");
        classroom.setBuilding("Troias");
        classroom.setFloor(3);
        classroom.setGeneralCapacity(100);
        classroom.setExamCapacity(70);
        classroom.setCovidCapacity(45);
        classroom.setMaxNumSupervisors(5);

        assertEquals(16, classroom.getId());
        assertEquals("C3", classroom.getName());
        assertEquals("Troias", classroom.getBuilding());
        assertEquals(3, classroom.getFloor());
        assertEquals(100, classroom.getGeneralCapacity());
        assertEquals(70, classroom.getExamCapacity());
        assertEquals(45, classroom.getCovidCapacity());
        assertEquals(5, classroom.getMaxNumSupervisors());
    }
}
