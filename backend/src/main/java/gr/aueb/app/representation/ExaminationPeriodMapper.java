package gr.aueb.app.representation;

import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.persistence.AcademicYearRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AcademicYearMapper.class})
public abstract class ExaminationPeriodMapper {

    @Inject
    AcademicYearRepository academicYearRepository;
    public abstract ExaminationPeriodRepresentation toRepresentation(ExaminationPeriod examinationPeriod);

    public abstract List<ExaminationPeriodRepresentation> toRepresentationList(List<ExaminationPeriod> examinationPeriods);

    @Mapping(target = "id", ignore = true)
    public abstract ExaminationPeriod toModel(ExaminationPeriodRepresentation representation);

    @AfterMapping
    protected void connectToAcademicYear(ExaminationPeriodRepresentation representation, @MappingTarget ExaminationPeriod examinationPeriod) {
        if (representation.academicYear != null && representation.academicYear.id != null) {
            AcademicYear academicYear = academicYearRepository.findById(representation.academicYear.id);
            if (academicYear == null) {
                throw new RuntimeException();
            }
            examinationPeriod.setAcademicYear(academicYear);
        }
    }
}
