package gr.aueb.app.resource;

import gr.aueb.app.application.CourseAttendanceService;
import gr.aueb.app.representation.CourseAttendanceMapper;
import gr.aueb.app.representation.CourseAttendanceRepresentation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static gr.aueb.app.resource.AppUri.COURSE_ATTENDANCES;

@Path(COURSE_ATTENDANCES)
@RequestScoped
public class CourseAttendanceResource {
    @Inject
    CourseAttendanceService courseAttendanceService;

    @Inject
    CourseAttendanceMapper courseAttendanceMapper;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadExcel(
            @QueryParam("examinationPeriod") Integer examinationPeriodId,
            @MultipartForm FormData formData) {
        if(examinationPeriodId == null) throw new BadRequestException();

        try (InputStream fileInputStream = formData.file) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            courseAttendanceService.upload(workbook, examinationPeriodId);
        } catch (Exception e) {
            // Handle exceptions, e.g., log or return an error response
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseAttendanceRepresentation> findAll() {
        return courseAttendanceMapper.toRepresentationList(courseAttendanceService.findAll());
    }

    @GET
    @Path("/generate")
    public void generate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet named "Sheet1"
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create headers in the first row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("course_code");
            headerRow.createCell(1).setCellValue("attendance");

            // Add data rows
            addDataRow(sheet, 1, "CS105", 230);
            addDataRow(sheet, 2, "AF101", 350);

            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("sample.xlsx")) {
                workbook.write(fileOut);
            }
            System.out.println("XLSX file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addDataRow(Sheet sheet, int rowNum, String courseCode, int attendance) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(courseCode);
        row.createCell(1).setCellValue(attendance);
    }
}
