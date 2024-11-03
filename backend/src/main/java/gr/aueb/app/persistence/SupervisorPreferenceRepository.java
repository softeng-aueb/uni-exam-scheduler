package gr.aueb.app.persistence;

import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.SupervisorPreference;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import java.util.List;

@ApplicationScoped
public class SupervisorPreferenceRepository implements PanacheRepositoryBase<SupervisorPreference, Integer> {
    public SupervisorPreference findSpecific(Integer supervisorId, Integer examinationPeriodId) {
        PanacheQuery<SupervisorPreference> query = find("select sp from SupervisorPreference sp where sp.examinationPeriod.id = :examinationPeriodId and sp.supervisor.id = :supervisorId",
                Parameters.with("examinationPeriodId", examinationPeriodId)
                        .and("supervisorId", supervisorId)
                        .map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<SupervisorPreference> findAllWithSameSupervisor(Integer supervisorId) {
        return find("select sp from SupervisorPreference sp where sp.supervisor.id = :supervisorId",
                Parameters.with("supervisorId", supervisorId)).list();
    }
}
