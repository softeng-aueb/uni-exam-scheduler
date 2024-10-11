package gr.aueb.app.application;

import gr.aueb.app.domain.Course;
import gr.aueb.app.domain.CourseAttendance;
import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.persistence.CourseAttendanceRepository;
import gr.aueb.app.persistence.CourseRepository;
import gr.aueb.app.persistence.ExaminationPeriodRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@RequestScoped
public class CourseAttendanceService {
    @Inject
    CourseRepository courseRepository;

    @Inject
    ExaminationPeriodRepository examinationPeriodRepository;

    @Inject
    CourseAttendanceRepository courseAttendanceRepository;

    @Transactional
    public void upload(Workbook workbook, Integer examinationPeriodId) {
        Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip the header row

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String courseCode = String.valueOf((int) row.getCell(0).getNumericCellValue());
            Integer attendance = (int) row.getCell(1).getNumericCellValue();

            Course course = courseRepository.findCourseByCode(courseCode);
            ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(examinationPeriodId);

            CourseAttendance courseAttendance = new CourseAttendance(attendance, course, examinationPeriod);
            courseAttendanceRepository.persist(courseAttendance);
        }
    }

    @Transactional
    public List<CourseAttendance> findAll() {
        return courseAttendanceRepository.findAllWithDetails();
    }
}
