package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SupervisionRepresentation {
    public Integer id;
    public Boolean isPresent;
    public Boolean isLead;
    public SupervisorRepresentation supervisor;
}
