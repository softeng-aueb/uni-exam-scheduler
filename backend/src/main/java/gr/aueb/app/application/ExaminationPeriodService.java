package gr.aueb.app.application;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.persistence.AcademicYearRepository;
import gr.aueb.app.persistence.ExaminationPeriodRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class ExaminationPeriodService {

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    AcademicYearRepository academicYearRepository;

    @Transactional
    public List<ExaminationPeriod> findAll() {
        return examinationPeriodRepository.listAll();
    }

    @Transactional
    public List<ExaminationPeriod> findAllInSameYear(Integer academicYearId) {
        return examinationPeriodRepository.findAllInSameYear(academicYearId);
    }

    @Transactional
    public ExaminationPeriod create(ExaminationPeriod newExaminationPeriod, Integer academicYearId) {
        AcademicYear foundAcademicYear = academicYearRepository.findById(academicYearId);
        newExaminationPeriod.setAcademicYear(foundAcademicYear);
        examinationPeriodRepository.persist(newExaminationPeriod);
        return newExaminationPeriod;
    }
}
