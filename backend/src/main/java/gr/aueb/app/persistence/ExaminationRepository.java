package gr.aueb.app.persistence;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.domain.CourseDeclaration;
import gr.aueb.app.domain.Examination;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import java.util.List;

@RequestScoped
public class ExaminationRepository implements PanacheRepositoryBase<Examination, Integer> {
    public Examination findWithDetails(Integer id) {
        PanacheQuery<Examination> query = find("select e from Examination e " +
                "left join fetch e.course " +
                "left join fetch e.supervisions " +
                "left join fetch e.classrooms " +
                "left join fetch e.examinationPeriod " +
                "where e.id = :id", Parameters.with("id", id).map());
        try {
            return query.singleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Examination> findAllInSamePeriod(Integer examinationPeriodId) {
       try {
           return find("select DISTINCT e from Examination e " +
                            "left join fetch e.course course " +
                            "left join fetch e.supervisions supervisions " +
                            "left join fetch e.classrooms " +
                            "left join fetch e.examinationPeriod examinationPeriod " +
                            "left join fetch course.department " +
                            "left join fetch supervisions.supervisor supervisor " +
                            "left join fetch supervisor.department " +
                            "left join fetch examinationPeriod.academicYear " +
                            "where e.examinationPeriod.id =:examinationPeriod",
                   Parameters.with("examinationPeriod", examinationPeriodId)).list();
       } catch (NoResultException ex) {
           return null;
       }
    }

    public List<Examination> findAllWithSameCourse(Integer courseId) {
        return find("select e from Examination e where e.course.id = :courseId",
                Parameters.with("courseId", courseId)).list();
    }

    public List<Examination> findAllWithSameClassroom(Classroom classroom) {
        return find("select e from Examination e where :classroom MEMBER OF e.classrooms",
                Parameters.with("classroom", classroom)).list();
    }
}
