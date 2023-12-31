package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SubjectRepresentation {
    public Integer id;
    public String title;
    public String subjectCode;
}
