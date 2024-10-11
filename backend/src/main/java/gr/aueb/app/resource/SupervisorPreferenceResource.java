package gr.aueb.app.resource;

import gr.aueb.app.application.SupervisorPreferenceService;
import gr.aueb.app.domain.SupervisorPreference;
import gr.aueb.app.representation.SupervisorPreferenceMapper;
import gr.aueb.app.representation.SupervisorPreferenceRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.InputStream;
import java.net.URI;

import static gr.aueb.app.resource.AppUri.SUPERVISOR_PREFERENCES;

@Path(SUPERVISOR_PREFERENCES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SupervisorPreferenceResource {

    @Inject
    SupervisorPreferenceMapper supervisorPreferenceMapper;

    @Inject
    SupervisorPreferenceService supervisorPreferenceService;

    @GET
    public SupervisorPreferenceRepresentation findSpecific(@QueryParam("supervisorId") Integer supervisorId,
                                 @QueryParam("examinationPeriodId") Integer examinationPeriodId) {
        if (supervisorId == null || examinationPeriodId == null) throw new BadRequestException();
        return supervisorPreferenceMapper.toRepresentation(supervisorPreferenceService.findSpecific(supervisorId, examinationPeriodId));
    }

    @POST
    public Response create(@QueryParam("supervisorId") Integer supervisorId,
                           @QueryParam("examinationPeriodId") Integer examinationPeriodId,
                           SupervisorPreferenceRepresentation representation) {
        SupervisorPreference newSupervisorPreference = supervisorPreferenceMapper.toModel(representation);
        if (newSupervisorPreference == null) throw new BadRequestException();
        SupervisorPreference createdSupervisorPreference = supervisorPreferenceService.create(newSupervisorPreference, supervisorId, examinationPeriodId);
        SupervisorPreferenceRepresentation response = supervisorPreferenceMapper.toRepresentation(createdSupervisorPreference);
        URI uri = UriBuilder.fromResource(SupervisorPreferenceResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadExcel(
            @QueryParam("examinationPeriod") Integer examinationPeriodId,
            @MultipartForm FormData formData) {
        if(examinationPeriodId == null) throw new BadRequestException();

        try (InputStream fileInputStream = formData.file) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            supervisorPreferenceService.upload(workbook, examinationPeriodId);
        } catch (Exception e) {
            // Handle exceptions, e.g., log or return an error response
            e.printStackTrace();
        }
    }
}
