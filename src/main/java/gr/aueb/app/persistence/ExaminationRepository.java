package gr.aueb.app.persistence;

import gr.aueb.app.domain.Examination;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class ExaminationRepository implements PanacheRepositoryBase<Examination, Integer> {
    public Examination findWithDetails(Integer id) {
        PanacheQuery<Examination> query = find("select e from Examination e " +
                "left join fetch e.subject " +
                "left join fetch e.departmentParticipations " +
                "left join fetch e.supervisions " +
                "left join fetch e.classrooms " +
                "left join fetch e.examinationPeriod " +
                "where e.id = :id", Parameters.with("id", id).map());

        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
