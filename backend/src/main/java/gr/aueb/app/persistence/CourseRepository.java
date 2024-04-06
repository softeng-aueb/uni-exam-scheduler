package gr.aueb.app.persistence;

import gr.aueb.app.domain.Course;
import gr.aueb.app.domain.CourseAttendance;
import gr.aueb.app.domain.CourseDeclaration;
import gr.aueb.app.domain.Examination;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class CourseRepository implements PanacheRepositoryBase<Course, Integer> {
    @Inject
    CourseAttendanceRepository courseAttendanceRepository;

    @Inject
    CourseDeclarationRepository courseDeclarationRepository;

    @Inject
    ExaminationRepository examinationRepository;

    public Course findWithDetails(Integer id) {
        PanacheQuery<Course> query = find("select c from Course c left join fetch c.department where c.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Course findCourseByCode(String courseCode) {
        PanacheQuery<Course> query = find("select c from Course c where c.courseCode = :courseCode", Parameters.with("courseCode", courseCode).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void delete(Integer courseId) {
        List<CourseAttendance> courseAttendances = courseAttendanceRepository.findAllWithSameCourse(courseId);
        List<CourseDeclaration> courseDeclarations = courseDeclarationRepository.findAllWithSameCourse(courseId);
        List<Examination> examinations = examinationRepository.findAllWithSameCourse(courseId);
        for (CourseAttendance attendance : courseAttendances) {
            attendance.setCourse(null);
            courseAttendanceRepository.getEntityManager().merge(attendance);
        }
        for (CourseDeclaration declaration : courseDeclarations) {
            declaration.setCourse(null);
            courseDeclarationRepository.getEntityManager().merge(declaration);
        }
        for (Examination examination : examinations) {
            examination.setCourse(null);
            examinationRepository.getEntityManager().merge(examination);
        }
        this.deleteById(courseId);
    }
}
