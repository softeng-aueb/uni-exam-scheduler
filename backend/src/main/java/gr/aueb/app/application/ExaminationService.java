package gr.aueb.app.application;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.persistence.SupervisionRepository;
import gr.aueb.app.persistence.SupervisorRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
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


    @Transactional
    public Examination create(Examination newExamination) {
        examinationRepository.persist(newExamination);
        return newExamination;
    }

    @Transactional
    public Examination findOne(Integer examinationId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination == null) {
            throw new NotFoundException();
        }
        return foundExamination;
    }

    @Transactional
    public List<Examination> findAll() {
        return examinationRepository.listAll();
    }

    @Transactional
    public List<Examination> findAllInSamePeriod(Integer examinationPeriodId) {
        return examinationRepository.findAllInSamePeriod(examinationPeriodId);
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
            throw new BadRequestException();
        }
        supervisionRepository.persist(addedSupervision);
        return addedSupervision;
    }

    @Transactional
    public void removeSupervision(Integer examinationId, Integer supervisionId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination ==  null) throw new NotFoundException();
        Supervision foundSupervision = supervisionRepository.findById(supervisionId);
        if(foundSupervision ==  null) throw new NotFoundException();
        if(!examinationId.equals(foundSupervision.getExamination().getId())) throw new BadRequestException();

        foundExamination.removeSupervision(foundSupervision);
        supervisionRepository.deleteById(supervisionId);
    }
}
