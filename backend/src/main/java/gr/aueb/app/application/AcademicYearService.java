package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.persistence.AcademicYearRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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

    @Transactional
    public AcademicYear create(AcademicYear newAcademicYear) {
        academicYearRepository.persist(newAcademicYear);
        return newAcademicYear;
    }

    @Transactional
    public void setActive(Integer id) {
        // first deactivate existing
        AcademicYear oldActive = academicYearRepository.findActive();
        if (oldActive != null) {
            oldActive.setIsActive(false);
        }
        // then activate current
        AcademicYear newActive = academicYearRepository.findById(id);
        if(newActive == null) {
            throw new NotFoundException();
        }
        newActive.setIsActive(true);
    }
}
