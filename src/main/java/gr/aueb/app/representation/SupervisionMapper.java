package gr.aueb.app.representation;

import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.persistence.SupervisorRepository;
import org.mapstruct.*;

import javax.inject.Inject;
import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ExaminationMapper.class, SupervisorMapper.class})
public abstract class SupervisionMapper {
    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    public abstract SupervisionRepresentation toRepresentation(Supervision supervision);
    public abstract List<SupervisionRepresentation> toRepresentationList(List<Supervision> supervisions);

    @Mapping(target = "isPresent", ignore = true)
    @Mapping(target = "isLead", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract Supervision toModel(SupervisionRepresentation representation);

    @AfterMapping
    protected void connectToExamination(SupervisionRepresentation representation,
                                    @MappingTarget Supervision supervision) {
        if (representation.examination != null || representation.examination.id != null) {
            Examination examination = examinationRepository.findById(Integer.valueOf(representation.examination.id));
            if (examination == null) {
                throw new RuntimeException();
            }
            supervision.setExamination(examination);
        }
    }

    @AfterMapping
    protected void connectToSupervisor(SupervisionRepresentation representation,
                                              @MappingTarget Supervision supervision) {
        if (representation.supervisor != null || representation.supervisor.id != null) {
            Supervisor supervisor = supervisorRepository.findById(Integer.valueOf(representation.supervisor.id));
            if (supervisor == null) {
                throw new RuntimeException();
            }
            supervision.setSupervisor(supervisor);
        }
    }
}
