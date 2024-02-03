package gr.aueb.app.representation;

import gr.aueb.app.domain.SupervisorCategory;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SupervisorRepresentation {
    public Integer id;
    public String name;
    public String surname;
    public String supervisor;
    public String telephone;
    public String email;
    public SupervisorCategory supervisorCategory;
    public DepartmentRepresentation department;
}
