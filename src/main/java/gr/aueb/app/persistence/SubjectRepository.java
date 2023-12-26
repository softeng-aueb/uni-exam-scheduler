package gr.aueb.app.persistence;

import gr.aueb.app.domain.Subject;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;

@RequestScoped
public class SubjectRepository implements PanacheRepositoryBase<Subject, Integer> {
    public Subject findWithDetails(Integer id) {
        PanacheQuery<Subject> query = find("select s from Subject s left join fetch s.departments where s.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
