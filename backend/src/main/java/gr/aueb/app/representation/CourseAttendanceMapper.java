package gr.aueb.app.representation;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.mapstruct.*;

import jakarta.inject.Inject;
import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CourseMapper.class, ExaminationPeriodMapper.class})
public abstract class CourseAttendanceMapper {
    @Inject
    CourseRepository courseRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    public abstract CourseAttendanceRepresentation toRepresentation(CourseAttendance courseAttendance);
    public abstract List<CourseAttendanceRepresentation> toRepresentationList(List<CourseAttendance> courseAttendances);
    @Mapping(target = "id", ignore = true)
    public abstract CourseAttendance toModel(CourseAttendanceRepresentation representation);

    @AfterMapping
    protected void connectToCourse(CourseAttendanceRepresentation representation,
                                        @MappingTarget CourseAttendance courseAttendance) {
        if (representation.course != null || representation.course.id != null) {
            Course course = courseRepository.findById(Integer.valueOf(representation.course.id));
            if (course == null) {
                throw new RuntimeException();
            }
            courseAttendance.setCourse(course);
        }
    }

    @AfterMapping
    protected void connectToExaminationPeriod(CourseAttendanceRepresentation representation,
                                   @MappingTarget CourseAttendance courseAttendance) {
        if (representation.examinationPeriod != null || representation.examinationPeriod.id != null) {
            ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(Integer.valueOf(representation.examinationPeriod.id));
            if (examinationPeriod == null) {
                throw new RuntimeException();
            }
            courseAttendance.setExaminationPeriod(examinationPeriod);
        }
    }
}
