package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ClassroomRepresentation {
    public Integer id;
    public String name;
    public String building;
    public Integer maxNumSupervisors;
}
