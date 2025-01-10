package gr.aueb.app.representation;

import gr.aueb.app.domain.Classroom;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ClassroomMapper {
    public abstract ClassroomRepresentation toRepresentation(Classroom classroom);
    public abstract List<ClassroomRepresentation> toRepresentationList(List<Classroom> classrooms);

    @Mapping(target = "id", ignore = true)
    public abstract Classroom toModel(ClassroomRepresentation representation);
}
