package gr.aueb.app.representation;

import gr.aueb.app.domain.DepartmentParticipation;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class DepartmentParticipationRepresentation {
    public Integer id;
    public Integer declaration;
    public Integer attendance;
    public Boolean isLeadDepartment;
    public ExaminationRepresentation examination;
    public DepartmentRepresentation department;
}
