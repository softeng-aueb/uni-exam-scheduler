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
    public Integer totalDeclaration;
    public Integer totalAttendance;
    public CourseRepresentation course;
    public List<ClassroomRepresentation> classrooms = new ArrayList<ClassroomRepresentation>();
    public ExaminationPeriodRepresentation examinationPeriod;
}
