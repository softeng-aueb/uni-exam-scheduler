package gr.aueb.app.application;

import gr.aueb.app.domain.Course;
import gr.aueb.app.domain.Department;
import gr.aueb.app.persistence.CourseRepository;

import gr.aueb.app.persistence.DepartmentRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@RequestScoped
public class CourseService {
    @Inject
    CourseRepository courseRepository;

    @Inject
    DepartmentRepository departmentRepository;

    @Transactional
    public Course create(Course newCourse, Integer departmentId) {
        Department foundDepartment = departmentRepository.findById(departmentId);
        newCourse.setDepartment(foundDepartment);
        courseRepository.persist(newCourse);
        return newCourse;
    }

    @Transactional
    public Course update(Integer courseId, Course updatedCourse) {
        Course foundCourse = courseRepository.findById(courseId);
        if(foundCourse == null) {
            throw new NotFoundException();
        }
        updatedCourse.setId(courseId);
        courseRepository.getEntityManager().merge(updatedCourse);
        return updatedCourse;
    }

    @Transactional
    public void delete(Integer courseId) {
        courseRepository.delete(courseId);
    }

    @Transactional
    public Course findOne(Integer courseId) {
        Course foundCourse = courseRepository.findById(courseId);
        if(foundCourse == null) {
            throw new NotFoundException();
        }
        return foundCourse;
    }

    @Transactional
    public List<Course> findAll() {
       return courseRepository.findAllWithDetails();
    }
}
