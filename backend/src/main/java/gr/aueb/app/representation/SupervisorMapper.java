package gr.aueb.app.representation;

import gr.aueb.app.domain.Department;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.persistence.DepartmentRepository;
import org.mapstruct.*;

import jakarta.inject.Inject;
import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {DepartmentMapper.class})
public abstract class SupervisorMapper {
    @Inject
    DepartmentRepository departmentRepository;

    public abstract SupervisorRepresentation toRepresentation(Supervisor supervisor);
    public abstract List<SupervisorRepresentation> toRepresentationList(List<Supervisor> supervisors);

    @Mapping(target = "id", ignore = true)
    public abstract Supervisor toModel(SupervisorRepresentation representation);

//    @AfterMapping
//    protected void connectToDepartment(SupervisorRepresentation representation, @MappingTarget Supervisor supervisor) {
//        if (representation.department != null && representation.department.id != null) {
//            Department department = departmentRepository.findById(representation.department.id);
//            if (department == null) {
//                throw new RuntimeException();
//            }
//            supervisor.setDepartment(department);
//        }
//    }
}
