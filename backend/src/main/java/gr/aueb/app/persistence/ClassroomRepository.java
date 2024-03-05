package gr.aueb.app.persistence;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
}
