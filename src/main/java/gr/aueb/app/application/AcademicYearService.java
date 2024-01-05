package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.persistence.AcademicYearRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class AcademicYearService {

    @Inject
    AcademicYearRepository academicYearRepository;

    @Transactional
    public List<AcademicYear> findAll() {
        try {
            List<AcademicYear> foundAcademicYears = academicYearRepository.listAll();
            return foundAcademicYears;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public AcademicYear findActive() {
        try {
            AcademicYear activeAcademicYear = academicYearRepository.findActive();
            return activeAcademicYear;
        } catch (Exception e) {
            throw e;
        }
    }
}
