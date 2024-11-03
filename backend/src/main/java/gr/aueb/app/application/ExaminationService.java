package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.time.*;
import java.util.*;

@RequestScoped
public class ExaminationService {

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    SupervisionRepository supervisionRepository;

    @Inject
    SupervisorRepository supervisorRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    ClassroomRepository classroomRepository;

    @Inject
    CourseDeclarationService courseDeclarationService;

    @Inject
    CourseAttendanceService courseAttendanceService;


    @Transactional
    public Examination create(Examination newExamination) {
        examinationRepository.persist(newExamination);
        return newExamination;
    }

    @Transactional
    public Examination findOne(Integer examinationId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination == null) {
            throw new NotFoundException();
        }
        return foundExamination;
    }

    @Transactional
    public List<Examination> findAll() {
        return examinationRepository.listAll();
    }

    @Transactional
    public List<Examination> findAllInSamePeriod(Integer examinationPeriodId) {
        List<Examination> foundList = examinationRepository.findAllInSamePeriod(examinationPeriodId);
        for(Examination examination : foundList) {
            CourseDeclaration courseDeclaration = courseDeclarationService.findSpecific(examination.getCourse().getId(), examination.getExaminationPeriod().getAcademicYear().getId());
            examination.setDeclaration(courseDeclaration);
            Double previousPercentage = getPreviousPercentage(examination);
            examination.setEstimatedAttendance(previousPercentage);
        }
        return foundList;
    }

    @Transactional
    public Supervision addSupervision(Integer examinationId, Integer supervisorId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination == null) throw new NotFoundException();
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        if(foundSupervisor == null) throw new NotFoundException();
        List<Supervision> foundSupervisions = supervisionRepository.findAllInSameSupervisorAndDay(supervisorId, foundExamination.getDate());

        Supervision addedSupervision = foundExamination.addSupervision(foundSupervisor, foundSupervisions);
        if(addedSupervision == null) {
            throw new BadRequestException();
        }
        supervisionRepository.persist(addedSupervision);
        return addedSupervision;
    }

    @Transactional
    public void removeSupervision(Integer examinationId, Integer supervisionId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination ==  null) throw new NotFoundException();
        Supervision foundSupervision = supervisionRepository.findById(supervisionId);
        if(foundSupervision ==  null) throw new NotFoundException();
        if(!examinationId.equals(foundSupervision.getExamination().getId())) throw new BadRequestException();

        foundExamination.removeSupervision(foundSupervision);
        supervisionRepository.deleteById(supervisionId);
    }

    @Transactional
    public void upload(Workbook workbook, Integer examinationPeriodId) {
        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet
        int totalSecondsInDay = 24 * 60 * 60;

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            LocalDate date = parseDate(row.getCell(0).getNumericCellValue());
            int seconds = (int) Math.round(row.getCell(1).getNumericCellValue() * totalSecondsInDay);
            LocalTime startTime = LocalTime.ofSecondOfDay(seconds);
            seconds = (int) Math.round(row.getCell(2).getNumericCellValue() * totalSecondsInDay);
            LocalTime endTime = LocalTime.ofSecondOfDay(seconds);
            String courseCode = String.valueOf((int) row.getCell(3).getNumericCellValue());
            String classroomsString = row.getCell(4).getStringCellValue();

            Course course = courseRepository.findCourseByCode(courseCode);
            ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(examinationPeriodId);
            Set<Classroom> classrooms = parseClassrooms(classroomsString);
            System.out.println(classrooms);
            Examination examination = new Examination(date, startTime, endTime, course, classrooms, examinationPeriod);
            examinationRepository.persist(examination);
        }
    }

    private Set<Classroom> parseClassrooms(String classroomsString) {
        Set<Classroom> classrooms = new HashSet<>();
        String[] classroomNames = classroomsString.split(",");
        for (String name : classroomNames) {
            Classroom classroom = classroomRepository.findByName(name);
            classrooms.add(classroom);
        }
        return classrooms;
    }

    private LocalDate parseDate(Double cellDate) {
        // Convert numeric value to LocalDate
        Date javaDate = DateUtil.getJavaDate(cellDate);
        Instant instant = Instant.ofEpochMilli(javaDate.getTime());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return zonedDateTime.toLocalDate();
    }

    private Double getPreviousPercentage(Examination examination) {
        // get declaration and attendance from 2 years back
        AcademicYear previousOneYear = examination.getExaminationPeriod().getAcademicYear().getPreviousYear();
        AcademicYear previousTwoYears = previousOneYear != null ? previousOneYear.getPreviousYear() : null;
        CourseDeclaration previousOneDeclaration = previousOneYear != null ? courseDeclarationService.findSpecific(examination.getCourse().getId(), previousOneYear.getId()) : null;
        CourseDeclaration previousTwoDeclaration = previousTwoYears != null ? courseDeclarationService.findSpecific(examination.getCourse().getId(), previousTwoYears.getId()) : null;
        CourseAttendance previousOneAttendance = previousOneYear != null ? courseAttendanceService.findSpecific(examination.getCourse().getId(), previousOneYear.getId(), examination.getExaminationPeriod().getPeriod()) : null;
        CourseAttendance previousTwoAttendance = previousTwoYears != null ? courseAttendanceService.findSpecific(examination.getCourse().getId(), previousTwoYears.getId(), examination.getExaminationPeriod().getPeriod()) : null;

        // estimation formula
        // if previousOne/TwoYear data is missing assuming that 4/5 was attended
        Double previousOnePercentage = ( previousOneDeclaration == null || previousOneAttendance == null ) ? (double) 4/5 : (double) previousOneAttendance.getAttendance()/previousOneDeclaration.getDeclaration();
        Double previousTwoPercentage = ( previousTwoDeclaration == null || previousTwoAttendance == null ) ? (double) 4/5 : (double) previousTwoAttendance.getAttendance()/previousTwoDeclaration.getDeclaration();
        return previousOnePercentage + previousTwoPercentage;
    }
}
