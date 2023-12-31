package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class ExaminationRepresentation {
    public Integer id;
    public String startDate;
    public String endDate;
    public Integer totalDeclaration;
    public Integer totalAttendance;
    public SubjectRepresentation subject;
    public List<ClassroomRepresentation> classrooms = new ArrayList<ClassroomRepresentation>();
    public ExaminationPeriodRepresentation examinationPeriod;
}
