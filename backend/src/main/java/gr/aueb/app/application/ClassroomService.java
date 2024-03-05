package gr.aueb.app.application;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.persistence.ClassroomRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RequestScoped
public class ClassroomService {
    @Inject
    ClassroomRepository classroomRepository;

    @Transactional
    public Classroom create(Classroom newClassroom) {
        classroomRepository.persist(newClassroom);
        return newClassroom;
    }

    @Transactional
    public Classroom update(Integer classroomId, Classroom updatedClassroom) {
        Classroom foundClassroom = classroomRepository.findById(classroomId);
        if(foundClassroom == null) {
            throw new NotFoundException();
        }
        updatedClassroom.setId(classroomId);
        classroomRepository.getEntityManager().merge(updatedClassroom);
        return updatedClassroom;
    }

    @Transactional
    public void delete(Integer classroomId) {
        classroomRepository.delete(classroomId);
    }

    @Transactional
    public Classroom findOne(Integer classroomId) {
        Classroom foundClassroom = classroomRepository.findById(classroomId);
        if(foundClassroom == null) {
            throw new NotFoundException();
        }
        return foundClassroom;
    }

    @Transactional
    public List<Classroom> findAll() {
        return classroomRepository.listAll();
    }
}
