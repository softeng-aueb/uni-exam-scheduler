package gr.aueb.app.persistence;

import gr.aueb.app.domain.CourseAttendance;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class CourseAttendanceRepository implements PanacheRepositoryBase<CourseAttendance, Integer> {
    public CourseAttendance findWithDetails(Integer id) {
        PanacheQuery<CourseAttendance> query = find("select ca from CourseAttendance ca" +
                "left join fetch ca.course" +
                "left join fetch ca.examinationPeriod" +
                "where ca.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
