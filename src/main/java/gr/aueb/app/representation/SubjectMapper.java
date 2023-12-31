package gr.aueb.app.representation;

import gr.aueb.app.domain.Subject;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SubjectMapper {
    public abstract SubjectRepresentation toRepresentation(Subject subject);

    public abstract List<SubjectRepresentation> toRepresentationList(List<Subject> subjects);
}
