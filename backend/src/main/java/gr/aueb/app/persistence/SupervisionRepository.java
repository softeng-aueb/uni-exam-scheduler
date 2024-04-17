package gr.aueb.app.persistence;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

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

    public List<Supervision> findAllInSameSupervisorAndDay(Integer supervisorId, LocalDate date) {
        return find("select s from Supervision s where s.supervisor.id = :supervisorId and s.examination.date = :date",
                Parameters.with("supervisorId", supervisorId).and("date", date)).list();
    }

    public List<Supervision> findAllWithSameSupervisor(Integer supervisorId) {
        return find("select s from Supervision s where s.supervisor.id = :supervisorId",
                Parameters.with("supervisorId", supervisorId)).list();
    }

    public List<Supervision> findAllInSamePeriod(Integer examinationPeriodId) {
        return find("select s from Supervision s where s.examination.examinationPeriod.id = :examinationPeriodId",
                Parameters.with("examinationPeriodId", examinationPeriodId)).list();
    }
}
