package gr.aueb.app.persistence;

import gr.aueb.app.domain.ExaminationPeriod;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class ExaminationPeriodRepository implements PanacheRepositoryBase<ExaminationPeriod, Integer> {

    public ExaminationPeriod findWithDetails(Integer id) {
        PanacheQuery<ExaminationPeriod> query = find("select e from ExaminationPeriod e left join fetch e.academicYear where e.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }

    }
}
