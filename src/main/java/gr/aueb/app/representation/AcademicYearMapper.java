package gr.aueb.app.representation;

import gr.aueb.app.domain.AcademicYear;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public class AcademicYearMapper {
    public String toString(AcademicYear academicYear) { return academicYear == null ? null : academicYear.getName(); }
}
