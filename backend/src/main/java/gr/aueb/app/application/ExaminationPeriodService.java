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
        try {
            List<ExaminationPeriod> foundExaminationPeriods = examinationPeriodRepository.listAll();
            return foundExaminationPeriods;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<ExaminationPeriod> findAllInSameYear(Integer academicYearId) {
        try {
            List<ExaminationPeriod> foundExaminationPeriods = examinationPeriodRepository.findAllInSameYear(academicYearId);
            return foundExaminationPeriods;
        } catch (Exception e) {
            throw e;
        }
    }
}
