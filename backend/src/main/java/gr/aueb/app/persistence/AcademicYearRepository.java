package gr.aueb.app.persistence;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

import gr.aueb.app.domain.AcademicYear;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

@RequestScoped
public class AcademicYearRepository implements PanacheRepositoryBase<AcademicYear, Integer>{
    public AcademicYear findActive() {
        try {
            return find("select ay from AcademicYear ay where ay.isActive =:isActive",
                    Parameters.with("isActive", true).map()).singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
