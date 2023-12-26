package gr.aueb.app.persistence;

import gr.aueb.app.domain.SupervisionRule;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class SupervisionRuleRepository implements PanacheRepositoryBase<SupervisionRule, Integer> {
    public SupervisionRule findWithDetails(Integer id) {
        PanacheQuery<SupervisionRule> query = find("select sr from SupervisionRule sr left join fetch sr.department left join fetch sr.examinationPeriod where sr.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
