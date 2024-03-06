package gr.aueb.app.application;

import gr.aueb.app.domain.*;
import gr.aueb.app.persistence.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
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
        return examinationRepository.findAllInSamePeriod(examinationPeriodId);
    }

    @Transactional
    public Supervision addSupervision(Integer examinationId, Integer supervisorId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        if(foundExamination ==  null) throw new NotFoundException();
        Supervisor foundSupervisor = supervisorRepository.findById(supervisorId);
        if(foundSupervisor ==  null) throw new NotFoundException();
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

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            LocalDate date = parseDate(row.getCell(0).getNumericCellValue());
            LocalTime startTime = LocalTime.parse(row.getCell(1).getStringCellValue());
            LocalTime endTime = LocalTime.parse(row.getCell(2).getStringCellValue());
            String courseCode = row.getCell(3).getStringCellValue();
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
}
