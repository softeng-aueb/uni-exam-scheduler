package gr.aueb.app.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CourseAttendanceRepresentation {
    public Integer id;
    public Integer attendance;
    public CourseRepresentation course;
    public ExaminationPeriodRepresentation examinationPeriod;
}
