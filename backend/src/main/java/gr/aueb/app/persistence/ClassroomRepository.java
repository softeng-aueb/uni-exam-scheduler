package gr.aueb.app.persistence;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.domain.Examination;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class ClassroomRepository implements PanacheRepositoryBase<Classroom, Integer> {
    @Inject
    ExaminationRepository examinationRepository;

    public void delete(Integer classroomId) {
        Classroom classroom = this.findById(classroomId);
        List<Examination> examinations = examinationRepository.findAllWithSameClassroom(classroom);
        for (Examination examination : examinations) {
            examination.removeClassroom(classroom);
            examinationRepository.getEntityManager().merge(examination);
        }
        this.deleteById(classroomId);
    }

    public Classroom findByName(String name) {
        PanacheQuery<Classroom> query = find("select c from Classroom c where c.name = :name", Parameters.with("name", name).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
