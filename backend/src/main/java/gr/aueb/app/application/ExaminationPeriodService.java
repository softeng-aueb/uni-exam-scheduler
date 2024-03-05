package gr.aueb.app.application;

import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.persistence.ExaminationPeriodRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
