package gr.aueb.app.persistence;

import gr.aueb.app.domain.Classroom;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ClassroomRepository implements PanacheRepositoryBase<Classroom, Integer> {
}
