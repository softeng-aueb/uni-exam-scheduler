package gr.aueb.app.representation;

import gr.aueb.app.domain.Period;
import gr.aueb.app.domain.Semester;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ExaminationPeriodRepresentation {
    public Integer id;
    public String startDate;
    public Period period;
    public AcademicYearRepresentation academicYear;
}
