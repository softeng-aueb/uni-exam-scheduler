package gr.aueb.app.representation;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.DepartmentRepository;
import gr.aueb.app.persistence.ExaminationRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import javax.inject.Inject;
import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ExaminationMapper.class, DepartmentMapper.class})
public abstract class DepartmentParticipationMapper {
    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    DepartmentRepository departmentRepository;

    public abstract DepartmentParticipationRepresentation toRepresentation(DepartmentParticipation departmentParticipation);
    public abstract List<DepartmentParticipationRepresentation> toRepresentationList(List<DepartmentParticipation> departmentParticipations);
    public abstract DepartmentParticipation toModel(DepartmentParticipationRepresentation representation);

    @AfterMapping
    protected void connectToExamination(DepartmentParticipationRepresentation representation,
                                        @MappingTarget DepartmentParticipation departmentParticipation) {
        if (representation.examination != null || representation.examination.id != null) {
            Examination examination = examinationRepository.findById(Integer.valueOf(representation.examination.id));
            if (examination == null) {
                throw new RuntimeException();
            }
            departmentParticipation.setExamination(examination);
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
}
