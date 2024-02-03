package gr.aueb.app.persistence;

import gr.aueb.app.domain.DepartmentParticipation;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class DepartmentParticipationRepository implements PanacheRepositoryBase<DepartmentParticipation, Integer> {
    public DepartmentParticipation findWithDetails(Integer id) {
        PanacheQuery<DepartmentParticipation> query = find("select dp from DepartmentParticipation dp" +
                "left join fetch dp.course" +
                "left join fetch dp.department" +
                "left join fetch dp.examinationPeriod" +
                "where dp.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
