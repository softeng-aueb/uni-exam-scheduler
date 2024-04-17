package gr.aueb.app.application;

import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.SupervisionRule;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.SupervisionRuleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SupervisionRuleService {

    @Transactional
    public static SupervisionRule findSupervisionRule(Supervision supervision, Supervisor supervisor) {
        SupervisionRuleRepository supervisionRuleRepository = new SupervisionRuleRepository(); // Assuming SupervisionRuleRepository is stateless and can be instantiated like this
        return supervisionRuleRepository.findSpecific(supervision.getExamination().getExaminationPeriod().getId(), supervisor.getDepartment().getId(), supervisor.getSupervisorCategory());
    }
}
