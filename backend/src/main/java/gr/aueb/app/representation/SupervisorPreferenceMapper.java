package gr.aueb.app.representation;

import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.domain.SupervisorPreference;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import gr.aueb.app.persistence.SupervisorRepository;
import jakarta.inject.Inject;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class SupervisorPreferenceMapper {

    public abstract SupervisorPreferenceRepresentation toRepresentation(SupervisorPreference supervisorPreference);

    public SupervisorPreference toModel(SupervisorPreferenceRepresentation representation) {
        LocalTime startTime = mapLocalTime(representation.startTime);
        LocalTime endTime = mapLocalTime(representation.endTime);
        Set<LocalDate> localDateSet = new HashSet<>();
        for (String dateString : representation.excludeDates) {
            LocalDate localDate = mapLocalDate(dateString);
            localDateSet.add(localDate);
        }
        return new SupervisorPreference(startTime, endTime, localDateSet, null, null);
    }

    @Named("mapLocalDate")
    public LocalDate mapLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

    @Named("mapLocalTime")
    public LocalTime mapLocalTime(String timeStr) {
        LocalTime localTime = LocalTime.parse(timeStr);
        return localTime;
    }
}

