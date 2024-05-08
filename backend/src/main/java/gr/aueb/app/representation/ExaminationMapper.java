package gr.aueb.app.representation;

import gr.aueb.app.domain.Classroom;
import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.domain.Course;
import gr.aueb.app.persistence.ClassroomRepository;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import gr.aueb.app.persistence.CourseRepository;
import org.mapstruct.*;

import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ClassroomMapper.class, ExaminationPeriodMapper.class, CourseMapper.class})
public abstract class ExaminationMapper {
    @Inject
    CourseRepository courseRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    ClassroomRepository classroomRepository;

    public abstract ExaminationRepresentation toRepresentation(Examination examination);
    public abstract List<ExaminationRepresentation> toRepresentationList(List<Examination> examinations);

    @Mapping(target = "date", source = "representation.date", qualifiedByName = "mapLocalDate")
    @Mapping(target = "startTime", source = "representation.startTime", qualifiedByName = "mapLocalTime")
    @Mapping(target = "endTime", source = "representation.endTime", qualifiedByName = "mapLocalTime")
    @Mapping(target = "totalDeclaration", ignore = true)
    @Mapping(target = "totalAttendance", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Examination toModel(ExaminationRepresentation representation);

    @AfterMapping
    protected void connectToCourse(ExaminationRepresentation representation,
                                    @MappingTarget Examination examination) {
        if (representation.course != null || representation.course.id != null) {
            Course course = courseRepository.findById(Integer.valueOf(representation.course.id));
            if (course == null) {
                throw new RuntimeException();
            }
            examination.setCourse(course);
        }
    }

    @AfterMapping
    protected void connectToExaminationPeriod(ExaminationRepresentation representation,
                                    @MappingTarget Examination examination) {
        if (representation.examinationPeriod != null || representation.examinationPeriod.id != null) {
            ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(Integer.valueOf(representation.examinationPeriod.id));
            if (examinationPeriod == null) {
                throw new RuntimeException();
            }
            examination.setExaminationPeriod(examinationPeriod);
        }
    }

    @AfterMapping
    protected void connectToClassrooms(ExaminationRepresentation representation,
                                 @MappingTarget Examination examination) {

        if (representation.classrooms != null && representation.classrooms.size() != 0) {
            Set<Classroom> classroomSet = new HashSet<>();
            representation.classrooms.forEach((classroom) -> {
                if(classroom != null){
                    Classroom classroomModel = classroomRepository.findById(Integer.valueOf(classroom.id));
                    classroomSet.add(classroomModel);
                }
            });
            examination.setClassrooms(classroomSet);
        }
    }

    @Named("mapLocalDate")
    public LocalDate mapLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    @Named("mapLocalTime")
    public LocalTime mapLocalTime(String timeStr) {
        LocalTime localTime = LocalTime.parse(timeStr);
        return localTime;
    }
}
