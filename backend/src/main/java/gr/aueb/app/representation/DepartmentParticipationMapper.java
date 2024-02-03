package gr.aueb.app.representation;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ExaminationMapper.class, DepartmentMapper.class})
public abstract class DepartmentParticipationMapper {
    @Inject
    CourseRepository courseRepository;

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    public abstract DepartmentParticipationRepresentation toRepresentation(DepartmentParticipation departmentParticipation);
    public abstract List<DepartmentParticipationRepresentation> toRepresentationList(List<DepartmentParticipation> departmentParticipations);
    @Mapping(target = "id", ignore = true)
    public abstract DepartmentParticipation toModel(DepartmentParticipationRepresentation representation);

    @AfterMapping
    protected void connectToCourse(DepartmentParticipationRepresentation representation,
                                        @MappingTarget DepartmentParticipation departmentParticipation) {
        if (representation.course != null || representation.course.id != null) {
            Course course = courseRepository.findById(Integer.valueOf(representation.course.id));
            if (course == null) {
                throw new RuntimeException();
            }
            departmentParticipation.setCourse(course);
        }
    }

    @AfterMapping
    protected void connectToDepartment(DepartmentParticipationRepresentation representation,
                                       @MappingTarget DepartmentParticipation departmentParticipation) {
        if (representation.department != null || representation.department.id != null) {
            Department department = departmentRepository.findById(Integer.valueOf(representation.department.id));
            if (department == null) {
                throw new RuntimeException();
            }
            departmentParticipation.setDepartment(department);
        }
    }

    @AfterMapping
    protected void connectToExaminationPeriod(DepartmentParticipationRepresentation representation,
                                   @MappingTarget DepartmentParticipation departmentParticipation) {
        if (representation.examinationPeriod != null || representation.examinationPeriod.id != null) {
            ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(Integer.valueOf(representation.examinationPeriod.id));
            if (examinationPeriod == null) {
                throw new RuntimeException();
            }
            departmentParticipation.setExaminationPeriod(examinationPeriod);
        }
    }
}
