package gr.aueb.app.representation;

import gr.aueb.app.domain.ExaminationPeriod;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AcademicYearMapper.class})
public abstract class ExaminationPeriodMapper {
    public abstract ExaminationPeriodRepresentation toRepresentation(ExaminationPeriod examinationPeriod);

    public abstract List<ExaminationPeriodRepresentation> toRepresentationList(List<ExaminationPeriod> examinationPeriods);
}
