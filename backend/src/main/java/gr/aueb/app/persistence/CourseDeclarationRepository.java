package gr.aueb.app.persistence;

import gr.aueb.app.domain.CourseDeclaration;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class CourseDeclarationRepository implements PanacheRepositoryBase<CourseDeclaration, Integer> {
    public List<CourseDeclaration> findAllWithSameCourse(Integer courseId) {
        return find("select cd from CourseDeclaration cd where cd.course.id = :courseId",
                Parameters.with("courseId", courseId)).list();
    }

    public List<CourseDeclaration> findAllWithDetails() {
        return find("select cd from CourseDeclaration cd " +
                "left join fetch cd.course course " +
                "left join fetch cd.academicYear " +
                "left join fetch course.department").list();
    }

    public List<CourseDeclaration> findAllInSameYear(Integer academicYearId) {
        return find("select cd from CourseDeclaration cd where cd.academicYear.id = :academicYearId",
                Parameters.with("academicYearId", academicYearId)).list();
    }

    public CourseDeclaration findSpecific(Integer courseId, Integer academicYearId) {
        PanacheQuery<CourseDeclaration> query = find("select cd from CourseDeclaration cd where cd.academicYear.id = :academicYearId and cd.course.id = :courseId",
                Parameters.with("academicYearId", academicYearId)
                        .and("courseId", courseId)
                        .map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
