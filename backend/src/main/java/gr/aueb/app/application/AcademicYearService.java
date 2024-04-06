package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.persistence.AcademicYearRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class AcademicYearService {

    @Inject
    AcademicYearRepository academicYearRepository;

    @Transactional
    public List<AcademicYear> findAll() {
        return academicYearRepository.listAll();
    }

    @Transactional
    public AcademicYear findActive() {
        return academicYearRepository.findActive();
    }
}
