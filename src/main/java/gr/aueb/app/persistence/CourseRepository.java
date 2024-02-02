package gr.aueb.app.persistence;

import gr.aueb.app.domain.Course;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class CourseRepository implements PanacheRepositoryBase<Course, Integer> {
    public Course findWithDetails(Integer id) {
        PanacheQuery<Course> query = find("select c from Course c left join fetch c.department where c.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
