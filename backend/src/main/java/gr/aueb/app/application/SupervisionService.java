package gr.aueb.app.application;

import gr.aueb.app.domain.Supervision;
import gr.aueb.app.persistence.SupervisionRepository;
import gr.aueb.app.representation.SupervisionMapper;
import gr.aueb.app.representation.SupervisionRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RequestScoped
public class SupervisionService {
    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisionMapper supervisionMapper;

    @Transactional
    public Supervision create(SupervisionRepresentation representation) {
        try {
            Supervision newSupervision = supervisionMapper.toModel(representation);
            supervisionRepository.persist(newSupervision);
            return newSupervision;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervision update(Integer supervisionId, SupervisionRepresentation representation) {
        try {
            Supervision updatedSupervision = supervisionMapper.toModel(representation);
            updatedSupervision.setId(supervisionId);
            supervisionRepository.getEntityManager().merge(updatedSupervision);
            return updatedSupervision;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void delete(Integer supervisionId) {
        try {
            supervisionRepository.deleteById(supervisionId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervision findOne(Integer supervisionId) {
        try {
            Supervision foundSupervision =  supervisionRepository.findById(supervisionId);
            if(foundSupervision ==  null) throw new NotFoundException();
            return foundSupervision;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Supervision> findAll() {
        try {
            List<Supervision> foundSupervisions = supervisionRepository.listAll();
            return foundSupervisions;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervision changePresentStatus(Integer supervisionId, Boolean isPresent) {
        try {
            Supervision supervision = supervisionRepository.findById(supervisionId);
            if(supervision ==  null) throw new NotFoundException();
            supervision.setIsPresent(isPresent);
            supervisionRepository.getEntityManager().merge(supervision);
            return supervision;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervision changeLeadStatus(Integer supervisionId, Boolean isLead) {
        try {
            Supervision supervision = supervisionRepository.findById(supervisionId);
            if(supervision ==  null) throw new NotFoundException();
            supervision.setIsLead(isLead);
            supervisionRepository.getEntityManager().merge(supervision);
            return supervision;
        } catch (Exception e) {
            throw e;
        }
    }
}
