package gr.aueb.app.representation;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CourseMapper.class, AcademicYearMapper.class})
public abstract class CourseDeclarationMapper {
    @Inject
    CourseRepository courseRepository;

    @Inject
    AcademicYearRepository academicYearRepository;

    public abstract CourseDeclarationRepresentation toRepresentation(CourseDeclaration courseDeclaration);
    public abstract List<CourseDeclarationRepresentation> toRepresentationList(List<CourseDeclaration> courseDeclarations);
    @Mapping(target = "id", ignore = true)
    public abstract CourseDeclaration toModel(CourseDeclarationRepresentation representation);

    @AfterMapping
    protected void connectToCourse(CourseDeclarationRepresentation representation,
                                   @MappingTarget CourseDeclaration courseDeclaration) {
        if (representation.course != null || representation.course.id != null) {
            Course course = courseRepository.findById(Integer.valueOf(representation.course.id));
            if (course == null) {
                throw new RuntimeException();
            }
            courseDeclaration.setCourse(course);
        }
    }

    @AfterMapping
    protected void connectToAcademicYear(CourseDeclarationRepresentation representation,
                                              @MappingTarget CourseDeclaration courseDeclaration) {
        if (representation.academicYear != null || representation.academicYear.id != null) {
            AcademicYear academicYear = academicYearRepository.findById(Integer.valueOf(representation.academicYear.id));
            if (academicYear == null) {
                throw new RuntimeException();
            }
            courseDeclaration.setAcademicYear(academicYear);
        }
    }
}
