package gr.aueb.app.application;

import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.SupervisorRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@RequestScoped
public class SupervisorService {
    @Inject
    SupervisorRepository supervisorRepository;

    @Transactional
    public Supervisor create(Supervisor newSupervisor) {
        supervisorRepository.persist(newSupervisor);
        return newSupervisor;
    }

    @Transactional
    public Supervisor update(Integer supervisorId, Supervisor updatedSupervisor) {
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        if(foundSupervisor == null) {
            throw new NotFoundException();
        }
        updatedSupervisor.setId(supervisorId);
        supervisorRepository.getEntityManager().merge(updatedSupervisor);
        return updatedSupervisor;
    }

    @Transactional
    public void delete(Integer supervisorId) {
        supervisorRepository.delete(supervisorId);
    }

    @Transactional
    public Supervisor findOne(Integer supervisorId) {
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        if(foundSupervisor == null) {
            throw new NotFoundException();
        }
        return foundSupervisor;
    }

    @Transactional
    public List<Supervisor> findAll() {
        return supervisorRepository.listAll();
    }

}
