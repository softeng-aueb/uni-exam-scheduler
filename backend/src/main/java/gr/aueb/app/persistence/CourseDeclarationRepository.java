package gr.aueb.app.persistence;

import gr.aueb.app.domain.CourseAttendance;
import gr.aueb.app.domain.CourseDeclaration;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class CourseDeclarationRepository implements PanacheRepositoryBase<CourseDeclaration, Integer> {
    public CourseDeclaration findWithDetails(Integer id) {
        PanacheQuery<CourseDeclaration> query = find("select cd from CourseDeclaration cd" +
                "left join fetch cd.course" +
                "left join fetch cd.academicYear" +
                "where cd.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    public List<CourseDeclaration> findAllWithSameCourse(Integer courseId) {
        return find("select cd from CourseDeclaration cd where cd.course.id = :courseId",
                Parameters.with("courseId", courseId)).list();
    }
}
