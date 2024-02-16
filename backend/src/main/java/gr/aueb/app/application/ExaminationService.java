package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.persistence.SupervisionRepository;
import gr.aueb.app.persistence.SupervisorRepository;
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
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisorRepository supervisorRepository;

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

    @Transactional
    public Supervision addSupervision(Integer examinationId, Integer supervisorId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination ==  null) throw new NotFoundException();
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        if(foundSupervisor ==  null) throw new NotFoundException();
        List<Supervision> foundSupervisions = supervisionRepository.findAllInSameSupervisorAndDay(supervisorId, foundExamination.getDate());

        Supervision addedSupervision = foundExamination.addSupervision(foundSupervisor, foundSupervisions);
        if(addedSupervision == null) {
            return null;
        }
        examinationRepository.getEntityManager().merge(foundExamination);
        return addedSupervision;
    }

    @Transactional
    public void removeSupervision(Integer examinationId, Integer supervisionId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination ==  null) throw new NotFoundException();
        Supervision foundSupervision = supervisionRepository.findById(supervisionId);
        if(foundSupervision ==  null) throw new NotFoundException();

        foundExamination.removeSupervision(foundSupervision);
        supervisionRepository.deleteById(supervisionId);
        examinationRepository.getEntityManager().merge(foundExamination);
    }
}
