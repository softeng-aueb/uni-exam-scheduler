package gr.aueb.app.representation;

import gr.aueb.app.domain.Department;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class DepartmentMapper {
    public abstract DepartmentRepresentation toRepresentation(Department department);
    public abstract List<DepartmentRepresentation> toRepresentationList(List<Department> departments);
}
