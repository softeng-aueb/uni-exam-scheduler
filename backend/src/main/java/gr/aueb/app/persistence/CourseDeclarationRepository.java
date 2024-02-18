package gr.aueb.app.persistence;

import gr.aueb.app.domain.CourseDeclaration;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

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
}
