package gr.aueb.app.persistence;

import gr.aueb.app.domain.SupervisionRule;
import gr.aueb.app.domain.SupervisorCategory;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class SupervisionRuleRepository implements PanacheRepositoryBase<SupervisionRule, Integer> {
    public SupervisionRule findSpecific(Integer examinationPeriodId, Integer departmentId, SupervisorCategory category) {
        PanacheQuery<SupervisionRule> query = find("select sr from SupervisionRule sr where sr.examinationPeriod.id = :examinationPeriodId and sr.department.id = :departmentId and sr.supervisorCategory = :category",
                Parameters.with("examinationPeriodId", examinationPeriodId)
                        .and("departmentId", departmentId)
                        .and("category", category)
                        .map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
