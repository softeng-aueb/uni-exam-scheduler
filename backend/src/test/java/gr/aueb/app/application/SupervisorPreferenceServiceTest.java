package gr.aueb.app.application;

import gr.aueb.app.domain.SupervisorPreference;
import gr.aueb.app.persistence.SupervisorPreferenceRepository;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class SupervisorPreferenceServiceTest {

    @Inject
    SupervisorPreferenceService supervisorPreferenceService;

    @Inject
    SupervisorPreferenceRepository supervisorPreferenceRepository;

    private Workbook workbook;

    @BeforeEach
    public void setup() {
        // Create a workbook with a single sheet and header row
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Supervisor Preferences");

        // Header row (skip this in the method)
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("email");
        headerRow.createCell(1).setCellValue("Start Time");
        headerRow.createCell(2).setCellValue("End Time");
        headerRow.createCell(3).setCellValue("Exclude Dates");

        // Sample data rows
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("anna.random5@example.com");
        dataRow.createCell(1).setCellValue(0.25);
        dataRow.createCell(2).setCellValue(0.5);
        dataRow.createCell(3).setCellValue(45325);

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("makis.random4@example.com");
        dataRow2.createCell(1).setCellValue(0.25);
        dataRow2.createCell(2).setCellValue(0.5);
        dataRow2.createCell(3).setCellValue("3/2/2024,7/2/2024,8/2/2024");
    }

    @Test
    @TestTransaction
    @Transactional
    public void findSpecific() {
        SupervisorPreference foundSupervisorPreference = supervisorPreferenceService.findSpecific(7001, 5004);

        assertEquals(12001, foundSupervisorPreference.getId());
        assertEquals(2, foundSupervisorPreference.getExcludeDates().size());
    }

    @Test
    @TestTransaction
    @Transactional
    public void testFindSpecificForSolver() {
        SupervisorPreference foundSupervisorPreference = SupervisorPreferenceService.findSpecificForSolver(7001, 5004);

        assertEquals(12001, foundSupervisorPreference.getId());
        assertEquals(2, foundSupervisorPreference.getExcludeDates().size());
    }


    @Test
    @TestTransaction
    @Transactional
    public void testUpload() {
        supervisorPreferenceService.upload(workbook, 5004);

        List<SupervisorPreference> supervisorPreferences = supervisorPreferenceRepository.listAll();
        SupervisorPreference supervisorPreference1 = supervisorPreferenceRepository.findSpecific(7005, 5004);
        SupervisorPreference supervisorPreference2 = supervisorPreferenceRepository.findSpecific(7004, 5004);

        assertEquals(4, supervisorPreferences.size());
        assertTrue(supervisorPreference1.getExcludeDates().contains(LocalDate.of(2024, 2, 3)));
        assertEquals(3, supervisorPreference2.getExcludeDates().size());
    }
}
