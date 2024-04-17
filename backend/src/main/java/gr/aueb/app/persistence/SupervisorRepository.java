package gr.aueb.app.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;

import gr.aueb.app.domain.*;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import java.util.List;

@ApplicationScoped
public class SupervisorRepository implements PanacheRepositoryBase<Supervisor, Integer> {
    @Inject
    SupervisionRepository supervisionRepository;

    public Supervisor findWithDetails(Integer id) {
        PanacheQuery<Supervisor> query = find("select s from Supervisor s left join fetch s.department where s.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    public void delete(Integer supervisorId) {
        List<Supervision> supervisions = supervisionRepository.findAllWithSameSupervisor(supervisorId);
        for (Supervision supervision : supervisions) {
            supervision.setSupervisor(null);
            supervisionRepository.getEntityManager().merge(supervision);
        }
        this.deleteById(supervisorId);
    }
}
