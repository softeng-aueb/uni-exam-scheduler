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
    public List<ExaminationPeriod> findAllInSameYear(Integer academicYearId) {
        try {
            return find("select ep from ExaminationPeriod ep left join fetch ep.academicYear where ep.academicYear.id =:academicYear",
                    Parameters.with("academicYear", academicYearId)).list();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
