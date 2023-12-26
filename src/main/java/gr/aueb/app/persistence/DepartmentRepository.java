package gr.aueb.app.persistence;

import gr.aueb.app.domain.Department;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DepartmentRepository implements PanacheRepositoryBase<Department, Integer> {
}
