package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.representation.ExaminationMapper;
import gr.aueb.app.representation.ExaminationRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RequestScoped
public class ExaminationService {

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    ExaminationMapper examinationMapper;

    @Transactional
    public Examination create(ExaminationRepresentation representation) {
        try {
            Examination newExamination = examinationMapper.toModel(representation);
            examinationRepository.persist(newExamination);
            return newExamination;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Examination findOne(Integer examinationId) {
        try {
            Examination foundExamination = examinationRepository.findById(examinationId);
            if(foundExamination ==  null) throw new NotFoundException();
            return foundExamination;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Examination> findAll() {
        try {
            List<Examination> foundExaminations = examinationRepository.listAll();
            return foundExaminations;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Examination> findAllInSamePeriod(Integer examinationPeriodId) {
        try {
            List<Examination> foundExaminations = examinationRepository.findAllInSamePeriod(examinationPeriodId);
            return foundExaminations;
        } catch (Exception e) {
            throw e;
        }
    }
}
