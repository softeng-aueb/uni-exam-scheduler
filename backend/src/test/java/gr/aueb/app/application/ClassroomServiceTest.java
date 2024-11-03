package gr.aueb.app.application;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.persistence.ClassroomRepository;

import io.quarkus.test.TestTransaction;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ClassroomServiceTest {

    @Inject
    ClassroomService classroomService;

    @Inject
    ClassroomRepository classroomRepository;

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllClassrooms() {
        List<Classroom> foundClassrooms = classroomService.findAll();
        assertNotNull(foundClassrooms);
        assertEquals(6, foundClassrooms.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindClassroom() {
        Classroom foundClassroom = classroomService.findOne(2003);
        assertNotNull(foundClassroom);
        assertEquals("C", foundClassroom.getName());
        assertEquals("Central", foundClassroom.getBuilding());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testCreateClassroom() {
        Classroom newClassroom = new Classroom("D3", "Main", 4, 70, 40, 25, 2);

        Classroom createdClassroom = classroomService.create(newClassroom);
        assertNotNull(createdClassroom);
        assertNotNull(createdClassroom.getId());
        assertEquals("D3", createdClassroom.getName());
        assertEquals(70, createdClassroom.getGeneralCapacity());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateClassroom() {
        Classroom updateClassroom = new Classroom("A1", "Central", 1, 260, 103, 70, 2);
        Classroom updatedClassroom = classroomService.update(2001, updateClassroom);

        assertNotNull(updateClassroom);
        assertEquals(2001, updatedClassroom.getId());
        assertEquals("A1", updatedClassroom.getName());
        assertEquals(260, updatedClassroom.getGeneralCapacity());
        assertEquals(2, updatedClassroom.getMaxNumSupervisors());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpdateClassroomFailed() {
        Classroom updateClassroom = new Classroom("A21", "Central", 1, 200, 140, 70, 6);
        assertThrows(NotFoundException.class, () ->
                classroomService.update(2010, updateClassroom));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindClassroomFailed() {
        assertThrows(NotFoundException.class, () ->
                classroomService.findOne(2010));
    }

    @Test
    @TestTransaction
    @Transactional
    public void testDeleteClassroom() {
        classroomService.delete(2001);
        List<Classroom> classrooms = classroomRepository.listAll();
        assertEquals(5, classrooms.size());
    }
}

