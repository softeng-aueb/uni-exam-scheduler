package gr.aueb.app.application;

import gr.aueb.app.domain.CourseAttendance;
import gr.aueb.app.domain.Period;
import gr.aueb.app.persistence.CourseAttendanceRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CourseAttendanceServiceTest {

    @Inject
    CourseAttendanceService courseAttendanceService;

    @Inject
    CourseAttendanceRepository courseAttendanceRepository;

    private Workbook workbook;

    @BeforeEach
    public void setup() {
        // Create a workbook with a single sheet and header row
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CourseAttendances");

        // Header row (skip this in the method)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Course Code");
        headerRow.createCell(1).setCellValue("Attendance");

        // Sample data row
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(3614);
        dataRow.createCell(1).setCellValue(267);
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<CourseAttendance> foundCourseAttendances = courseAttendanceService.findAll();

        assertEquals(4, foundCourseAttendances.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindSpecific() {
        CourseAttendance foundCourseAttendance = courseAttendanceService.findSpecific(4001, 1002, Period.WINTER);

        assertEquals(200, foundCourseAttendance.getAttendance());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpload() {
        courseAttendanceService.upload(workbook, 5004);
        List<CourseAttendance> foundCourseAttendances = courseAttendanceRepository.listAll();

        assertEquals(5, foundCourseAttendances.size());
    }
}
