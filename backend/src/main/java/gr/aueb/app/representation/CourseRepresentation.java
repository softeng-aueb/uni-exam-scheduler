package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CourseRepresentation {
    public Integer id;
    public String title;
    public String courseCode;

    public DepartmentRepresentation department;
}
