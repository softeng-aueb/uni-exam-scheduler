package gr.aueb.app.persistence;

import gr.aueb.app.domain.Supervision;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class SupervisionRepository implements PanacheRepositoryBase<Supervision, Integer> {
    public Supervision findWithDetails(Integer id) {
        PanacheQuery<Supervision> query = find("select sr from Supervision s left join fetch s.examination left join fetch s.supervisor where s.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
