package gr.aueb.app.application;

import gr.aueb.app.domain.CourseDeclaration;
import gr.aueb.app.persistence.CourseDeclarationRepository;
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
public class CourseDeclarationServiceTest {

    @Inject
    CourseDeclarationService courseDeclarationService;

    @Inject
    CourseDeclarationRepository courseDeclarationRepository;

    private Workbook workbook;

    @BeforeEach
    public void setup() {
        // Create a workbook with a single sheet and header row
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CourseAttendances");

        // Header row (skip this in the method)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Course Code");
        headerRow.createCell(1).setCellValue("Declaration");

        // Sample data row
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(3614);
        dataRow.createCell(1).setCellValue(267);
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAll() {
        List<CourseDeclaration> foundCourseDeclarations = courseDeclarationService.findAll();

        assertEquals(2, foundCourseDeclarations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindSpecific() {
        CourseDeclaration foundCourseDeclaration = courseDeclarationService.findSpecific(4001, 1002);

        assertEquals(400, foundCourseDeclaration.getDeclaration());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindAllInSameYear() {
        List<CourseDeclaration> foundCourseDeclarations = courseDeclarationService.findAllInSameYear(1002);

        assertEquals(2, foundCourseDeclarations.size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testUpload() {
        courseDeclarationService.upload(workbook, 1002);
        List<CourseDeclaration> foundCourseDeclarations = courseDeclarationRepository.listAll();

        assertEquals(3, foundCourseDeclarations.size());
    }
}
