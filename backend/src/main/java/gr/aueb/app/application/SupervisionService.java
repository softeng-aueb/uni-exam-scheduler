package gr.aueb.app.application;

import gr.aueb.app.domain.Supervision;
import gr.aueb.app.persistence.SupervisionRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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
        return supervisionRepository.findAllWithDetails();
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
