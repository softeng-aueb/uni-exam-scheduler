package gr.aueb.app.application;

import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorPreference;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import gr.aueb.app.persistence.SupervisorPreferenceRepository;
import gr.aueb.app.persistence.SupervisorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class SupervisorPreferenceService {
    @Inject
    SupervisorPreferenceRepository supervisorPreferenceRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Transactional
    public SupervisorPreference create(SupervisorPreference newSupervisorPreference, Integer supervisorId, Integer examinationPeriodId) {
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        ExaminationPeriod foundExaminationPeriod = examinationPeriodRepository.findById(examinationPeriodId);
        if(foundSupervisor == null || foundExaminationPeriod == null) throw new BadRequestException();
        newSupervisorPreference.setSupervisor(foundSupervisor);
        newSupervisorPreference.setExaminationPeriod(foundExaminationPeriod);
        supervisorPreferenceRepository.persist(newSupervisorPreference);
        System.out.println(newSupervisorPreference.getExaminationPeriod().getStartDate());
        return newSupervisorPreference;
    }

    @Transactional
    public SupervisorPreference findSpecific(Integer supervisorId, Integer examinationPeriodId) {
        SupervisorPreference foundSupervisorPreference = supervisorPreferenceRepository.findSpecific(supervisorId, examinationPeriodId);
        if(foundSupervisorPreference == null) throw new NotFoundException();
        return foundSupervisorPreference;
    }

    @Transactional
    public static SupervisorPreference findSpecificForSolver(Integer supervisorId, Integer examinationPeriodId) {
        SupervisorPreferenceRepository supervisorPreferenceRepositoryLocal = new SupervisorPreferenceRepository(); // Assuming SupervisorPreferenceRepository is stateless and can be instantiated like this
        return supervisorPreferenceRepositoryLocal.findSpecific(supervisorId, examinationPeriodId);
    }
}
