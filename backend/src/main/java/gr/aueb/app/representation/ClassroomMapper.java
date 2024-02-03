package gr.aueb.app.representation;

import gr.aueb.app.domain.Classroom;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ClassroomMapper {
    public abstract ClassroomRepresentation toRepresentation(Classroom classroom);
    public abstract List<ClassroomRepresentation> toRepresentationList(List<Classroom> classrooms);
}
