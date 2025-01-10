package gr.aueb.app.application;

import gr.aueb.app.domain.SupervisionRule;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.SupervisionRuleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SupervisionRuleService {

    @Transactional
    public static SupervisionRule findSupervisionRule(Integer examinationPeriodId, Supervisor supervisor) {
        SupervisionRuleRepository supervisionRuleRepository = new SupervisionRuleRepository(); // Assuming SupervisionRuleRepository is stateless and can be instantiated like this
        return supervisionRuleRepository.findSpecific(examinationPeriodId, supervisor.getDepartment().getId(), supervisor.getSupervisorCategory());
    }
}
