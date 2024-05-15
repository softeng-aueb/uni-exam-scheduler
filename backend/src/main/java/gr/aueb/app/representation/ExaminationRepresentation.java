package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class ExaminationRepresentation {
    public Integer id;
    public String date;
    public String startTime;
    public String endTime;
    public Integer declaration;
    public Integer estimatedAttendance;
    public Integer estimatedSupervisors;
    public Integer maxSupervisors;
    public List<SupervisionRepresentation> supervisions = new ArrayList<SupervisionRepresentation>();
    public CourseRepresentation course;
    public List<ClassroomRepresentation> classrooms = new ArrayList<ClassroomRepresentation>();
    public ExaminationPeriodRepresentation examinationPeriod;
}
