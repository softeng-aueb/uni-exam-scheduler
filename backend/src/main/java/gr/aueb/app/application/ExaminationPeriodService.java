package gr.aueb.app.application;

import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.persistence.ExaminationPeriodRepository;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@RequestScoped
public class ExaminationPeriodService {

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Transactional
    public List<ExaminationPeriod> findAll() {
        return examinationPeriodRepository.listAll();
    }

    @Transactional
    public List<ExaminationPeriod> findAllInSameYear(Integer academicYearId) {
        return examinationPeriodRepository.findAllInSameYear(academicYearId);
    }
}
