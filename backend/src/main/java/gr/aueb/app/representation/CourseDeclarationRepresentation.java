package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CourseDeclarationRepresentation {
    public Integer id;
    public Integer declaration;
    public CourseRepresentation course;
    public AcademicYearRepresentation academicYear;
}