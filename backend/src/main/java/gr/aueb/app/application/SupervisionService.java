package gr.aueb.app.application;

import gr.aueb.app.domain.Supervision;
import gr.aueb.app.persistence.SupervisionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class SupervisionService {
    @Inject
    SupervisionRepository supervisionRepository;

    @Transactional
    public Supervision findOne(Integer supervisionId) {
        Supervision foundSupervision = supervisionRepository.findById(supervisionId);
        if(foundSupervision == null) {
            throw new NotFoundException();
        }
        return foundSupervision;
    }

    @Transactional
    public List<Supervision> findAll() {
        return supervisionRepository.listAll();
    }

    @Transactional
    public Supervision changePresentStatus(Integer supervisionId, Boolean isPresent) {
        Supervision supervision = supervisionRepository.findById(supervisionId);
        if(supervision ==  null) throw new NotFoundException();
        supervision.setIsPresent(isPresent);
        supervisionRepository.getEntityManager().merge(supervision);
        return supervision;
    }

    @Transactional
    public Supervision changeLeadStatus(Integer supervisionId, Boolean isLead) {
        Supervision supervision = supervisionRepository.findById(supervisionId);
        if(supervision ==  null) throw new NotFoundException();
        supervision.setIsLead(isLead);
        return supervision;
    }

    @Transactional
    public List<Supervision> findAllInSameSupervisorAndDay(Integer supervisorId, LocalDate date) {
        return supervisionRepository.findAllInSameSupervisorAndDay(supervisorId, date);
    }
}
