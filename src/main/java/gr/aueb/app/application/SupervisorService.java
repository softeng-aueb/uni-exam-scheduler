package gr.aueb.app.application;

import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.SupervisorRepository;
import gr.aueb.app.representation.SupervisorMapper;
import gr.aueb.app.representation.SupervisorRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class SupervisorService {
    @Inject
    SupervisorRepository supervisorRepository;

    @Inject
    SupervisorMapper supervisorMapper;

    @Transactional
    public Supervisor create(SupervisorRepresentation representation) {
        try {
            Supervisor newSupervisor = supervisorMapper.toModel(representation);
            supervisorRepository.persist(newSupervisor);
            return newSupervisor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervisor update(Integer supervisorId, SupervisorRepresentation representation) {
        try {
            Supervisor updatedSupervisor = supervisorMapper.toModel(representation);
            updatedSupervisor.setId(supervisorId);
            supervisorRepository.getEntityManager().merge(updatedSupervisor);
            return updatedSupervisor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void delete(Integer supervisorId) {
        try {
            supervisorRepository.deleteById(supervisorId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Supervisor findOne(Integer supervisorId) {
        try {
            Supervisor foundSupervisor =  supervisorRepository.findById(supervisorId);
            return foundSupervisor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public List<Supervisor> findAll() {
        try {
            List<Supervisor> foundSupervisors = supervisorRepository.listAll();
            return foundSupervisors;
        } catch (Exception e) {
            throw e;
        }
    }

}
