package gr.aueb.app.representation;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.persistence.AcademicYearRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AcademicYearMapper {

    @Inject
    AcademicYearRepository academicYearRepository;
//    public String toString(AcademicYear academicYear) { return academicYear == null ? null : academicYear.getName(); }
    public abstract AcademicYearRepresentation toRepresentation(AcademicYear academicYear);

    public abstract List<AcademicYearRepresentation> toRepresentationList(List<AcademicYear> academicYears);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    public abstract AcademicYear toModel(AcademicYearRepresentation representation);

    @AfterMapping
    protected void connectToPreviousYear(AcademicYearRepresentation representation, @MappingTarget AcademicYear academicYear) {
        if (representation.previousYear != null && representation.previousYear.id != null) {
            AcademicYear previousYear = academicYearRepository.findById(representation.previousYear.id);
            if (previousYear == null) {
                throw new RuntimeException();
            }
            academicYear.setPreviousYear(previousYear);
        }
    }
}
