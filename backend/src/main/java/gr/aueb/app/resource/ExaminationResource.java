package gr.aueb.app.resource;

import gr.aueb.app.application.ExaminationService;
import gr.aueb.app.domain.CourseAttendance;
import gr.aueb.app.domain.Examination;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.persistence.CourseAttendanceRepository;
import gr.aueb.app.persistence.ExaminationRepository;
import gr.aueb.app.representation.*;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.EXAMINATIONS;

@Path(EXAMINATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ExaminationResource {

    @Inject
    ExaminationService examinationService;

    @Inject
    ExaminationMapper examinationMapper;

    @Inject
    SupervisionMapper supervisionMapper;

    @Inject
    ExaminationRepository examinationRepository;

    @Inject
    CourseAttendanceRepository courseAttendanceRepository;

    @Context
    UriInfo uriInfo;

    @GET
    public List<ExaminationRepresentation> findAllInSamePeriod(@QueryParam("examinationPeriod") Integer examinationPeriodId) {
        if(examinationPeriodId == null) throw new BadRequestException();
        return examinationMapper.toRepresentationList(examinationService.findAllInSamePeriod(examinationPeriodId));
    }


    @GET
    @Path("/{examinationId}/estimations")
    @Transactional
    public Response getEstimations(@PathParam("examinationId") Integer examinationId) {
        Examination foundExamination = examinationRepository.findById(examinationId);
        CourseAttendance foundAttendance = courseAttendanceRepository.findSpecific(foundExamination.getCourse().getId(), foundExamination.getExaminationPeriod().getAcademicYear().getId(), foundExamination.getExaminationPeriod().getPeriod());
        System.out.println(foundAttendance.getAttendance());
        System.out.println(foundExamination.getDeclaration());
        return Response.ok().build();
    }

    @POST
    @Path("/{examinationId}/supervisions")
    public Response addSupervision(@PathParam("examinationId") Integer examinationId, SupervisorRepresentation representation) {
        // TODO check if toModel makes the validation we want
        Supervision addedSupervision = examinationService.addSupervision(examinationId, representation.id);
        SupervisionRepresentation response = supervisionMapper.toRepresentation(addedSupervision);
        URI uri = UriBuilder.fromResource(ExaminationResource.class).path(String.valueOf(examinationId)).build();
        return Response.created(uri).entity(response).build();
    }

    @DELETE
    @Path("/{examinationId}/supervisions/{supervisionId}")
    public Response removeSupervision(@PathParam("examinationId") Integer examinationId,
                                      @PathParam("supervisionId") Integer supervisionId) {
        examinationService.removeSupervision(examinationId, supervisionId);
        return Response.noContent().build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadExcel(
            @QueryParam("examinationPeriodId") Integer examinationPeriodId,
            @MultipartForm FormData formData) {
        if(examinationPeriodId == null) throw new BadRequestException();

        try (InputStream fileInputStream = formData.file) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            examinationService.upload(workbook, examinationPeriodId);
        } catch (Exception e) {
            // Handle exceptions, e.g., log or return an error response
            e.printStackTrace();
        }
    }
}
