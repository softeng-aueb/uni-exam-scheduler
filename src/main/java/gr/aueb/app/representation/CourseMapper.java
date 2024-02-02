package gr.aueb.app.representation;

import gr.aueb.app.domain.Course;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class CourseMapper {
    public abstract CourseRepresentation toRepresentation(Course course);

    public abstract List<CourseRepresentation> toRepresentationList(List<Course> courses);
}
