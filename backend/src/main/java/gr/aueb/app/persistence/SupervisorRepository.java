package gr.aueb.app.persistence;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

import gr.aueb.app.domain.Supervisor;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class SupervisorRepository implements PanacheRepositoryBase<Supervisor, Integer>{
    public Supervisor findWithDetails(Integer id) {
        PanacheQuery<Supervisor> query = find("select s from Supervisor s left join fetch s.department where s.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
