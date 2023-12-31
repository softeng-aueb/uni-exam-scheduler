package gr.aueb.app.application;

import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.SupervisorRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@RequestScoped
public class SupervisorService {
    @Inject
    SupervisorRepository supervisorRepository;
}
