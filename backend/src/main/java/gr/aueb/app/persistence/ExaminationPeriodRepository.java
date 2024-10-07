package gr.aueb.app.persistence;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.ExaminationPeriod;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;
import java.util.List;

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

    public List<ExaminationPeriod> findAllInSameYear(Integer academicYearId) {
        try {
            return find("select ep from ExaminationPeriod ep left join fetch ep.academicYear where ep.academicYear.id =:academicYear",
                    Parameters.with("academicYear", academicYearId)).list();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
