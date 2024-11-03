package gr.aueb.app.resource;

import gr.aueb.app.application.CourseDeclarationService;
import gr.aueb.app.representation.CourseDeclarationMapper;
import gr.aueb.app.representation.CourseDeclarationRepresentation;
import jakarta.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

import static gr.aueb.app.resource.AppUri.COURSE_DECLARATIONS;

@Path(COURSE_DECLARATIONS)
@RequestScoped
public class CourseDeclarationResource {
    @Inject
    CourseDeclarationService courseDeclarationService;

    @Inject
    CourseDeclarationMapper courseDeclarationMapper;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadExcel(
            @QueryParam("academicYearId") Integer academicYearId,
            @MultipartForm FormData formData) {
        if (academicYearId == null) throw new BadRequestException();

        try (InputStream fileInputStream = formData.file) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            courseDeclarationService.upload(workbook, academicYearId);
            String jsonResponse = "{\"status\": \"Success\"}";
            return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            // Handle exceptions, e.g., log or return an error response
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseDeclarationRepresentation> findAll() {
        return courseDeclarationMapper.toRepresentationList(courseDeclarationService.findAll());
    }
}
