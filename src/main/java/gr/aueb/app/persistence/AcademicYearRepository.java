package gr.aueb.app.persistence;

import javax.enterprise.context.RequestScoped;

import gr.aueb.app.domain.AcademicYear;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AcademicYearRepository implements PanacheRepositoryBase<AcademicYear, Integer>{
}
