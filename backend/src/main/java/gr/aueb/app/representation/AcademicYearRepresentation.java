package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AcademicYearRepresentation {
    public Integer id;
    public String name;
    public Boolean isActive;
    public AcademicYearRepresentation previousYear;
}
