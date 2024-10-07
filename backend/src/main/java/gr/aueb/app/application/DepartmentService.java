package gr.aueb.app.application;

import gr.aueb.app.domain.Department;
import gr.aueb.app.persistence.DepartmentRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    @Transactional
    public List<Department> findAll() {
        return departmentRepository.listAll();
    }
}
