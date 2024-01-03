package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DepartmentParticipationRepresentation {
    public Integer id;
    public Integer declaration;
    public Boolean isLeadDepartment;
    public ExaminationRepresentation examination;
    public DepartmentRepresentation department;
}
