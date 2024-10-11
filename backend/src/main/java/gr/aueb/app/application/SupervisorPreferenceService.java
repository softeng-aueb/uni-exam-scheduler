package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import gr.aueb.app.persistence.SupervisorPreferenceRepository;
import gr.aueb.app.persistence.SupervisorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.apache.poi.ss.usermodel.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@ApplicationScoped
public class SupervisorPreferenceService {
    @Inject
    SupervisorPreferenceRepository supervisorPreferenceRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Transactional
    public SupervisorPreference create(SupervisorPreference newSupervisorPreference, Integer supervisorId, Integer examinationPeriodId) {
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        ExaminationPeriod foundExaminationPeriod = examinationPeriodRepository.findById(examinationPeriodId);
        if(foundSupervisor == null || foundExaminationPeriod == null) throw new BadRequestException();
        newSupervisorPreference.setSupervisor(foundSupervisor);
        newSupervisorPreference.setExaminationPeriod(foundExaminationPeriod);
        supervisorPreferenceRepository.persist(newSupervisorPreference);
        System.out.println(newSupervisorPreference.getExaminationPeriod().getStartDate());
        return newSupervisorPreference;
    }

    @Transactional
    public SupervisorPreference findSpecific(Integer supervisorId, Integer examinationPeriodId) {
        SupervisorPreference foundSupervisorPreference = supervisorPreferenceRepository.findSpecific(supervisorId, examinationPeriodId);
        if(foundSupervisorPreference == null) throw new NotFoundException();
        return foundSupervisorPreference;
    }

    @Transactional
    public static SupervisorPreference findSpecificForSolver(Integer supervisorId, Integer examinationPeriodId) {
        SupervisorPreferenceRepository supervisorPreferenceRepositoryLocal = new SupervisorPreferenceRepository(); // Assuming SupervisorPreferenceRepository is stateless and can be instantiated like this
        return supervisorPreferenceRepositoryLocal.findSpecific(supervisorId, examinationPeriodId);
    }

    @Transactional
    public void upload(Workbook workbook, Integer examinationPeriodId) {
        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
        int totalSecondsInDay = 24 * 60 * 60;

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String email = row.getCell(0).getStringCellValue();
            int seconds = (int) Math.round(row.getCell(1).getNumericCellValue() * totalSecondsInDay);
            LocalTime startTime = LocalTime.ofSecondOfDay(seconds);
            seconds = (int) Math.round(row.getCell(2).getNumericCellValue() * totalSecondsInDay);
            LocalTime endTime = LocalTime.ofSecondOfDay(seconds);

            Set<LocalDate> excludeDates = new HashSet<>();
            if (row.getCell(3).getCellType() == CellType.NUMERIC) { //only one exclude date
                LocalDate date = parseDate(row.getCell(3).getNumericCellValue());
                excludeDates.add(date);
            } else { // list of exclude dates
                String excludeDatesString = row.getCell(3).getStringCellValue();
                excludeDates = parseExcludeDates(excludeDatesString, excludeDates);
            }

            //find supervisorByEmail
            Supervisor foundSupervisor = supervisorRepository.findByEmail(email);
            if(foundSupervisor == null) throw new NotFoundException();
            ExaminationPeriod foundExaminationPeriod = examinationPeriodRepository.findById(examinationPeriodId);
            if(foundExaminationPeriod == null) throw new NotFoundException();

            System.out.println(excludeDates);
            SupervisorPreference supervisorPreference = new SupervisorPreference(startTime, endTime, excludeDates, foundSupervisor, foundExaminationPeriod);
            supervisorPreferenceRepository.persist(supervisorPreference);
        }
    }

    private Set<LocalDate> parseExcludeDates(String excludeDatesString, Set<LocalDate> excludeDatesSet) {
        String[] excludeDates = excludeDatesString.split(",");
        for (String excludeDate : excludeDates) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(excludeDate, formatter);
            excludeDatesSet.add(date);
        }
        return excludeDatesSet;
    }

    private LocalDate parseDate(Double cellDate) {
        // Convert numeric value to LocalDate
        Date javaDate = DateUtil.getJavaDate(cellDate);
        Instant instant = Instant.ofEpochMilli(javaDate.getTime());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return zonedDateTime.toLocalDate();
    }
}
