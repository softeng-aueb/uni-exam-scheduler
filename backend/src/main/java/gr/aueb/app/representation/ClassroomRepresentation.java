package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ClassroomRepresentation {
    public Integer id;
    public String name;
    public String building;
    public Integer floor;
    public Integer generalCapacity;
    public Integer examCapacity;
    public Integer covidCapacity;
    public Integer maxNumSupervisors;
}
