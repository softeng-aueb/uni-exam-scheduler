package gr.aueb.app.resource;

import gr.aueb.app.application.ExaminationPeriodService;
import gr.aueb.app.domain.ExaminationPeriod;
import gr.aueb.app.representation.ExaminationPeriodMapper;
import gr.aueb.app.representation.ExaminationPeriodRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.EXAMINATION_PERIODS;

@Path(EXAMINATION_PERIODS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ExaminationPeriodResource {

    @Inject
    ExaminationPeriodService examinationPeriodService;

    @Inject
    ExaminationPeriodMapper examinationPeriodMapper;

    @Context
    UriInfo uriInfo;

    @GET
    public List<ExaminationPeriodRepresentation> findAllInSameYear(@QueryParam("academicYearId") Integer academicYearId) {
        return examinationPeriodMapper.toRepresentationList(examinationPeriodService.findAllInSameYear(academicYearId));
    }

    @POST
    public Response create(ExaminationPeriodRepresentation representation) {
        // TODO check if toModel makes the validation we want
        ExaminationPeriod newExaminationPeriod = examinationPeriodMapper.toModel(representation);
        ExaminationPeriod createdExaminationPeriod = examinationPeriodService.create(newExaminationPeriod, newExaminationPeriod.getAcademicYear().getId());
        ExaminationPeriodRepresentation response = examinationPeriodMapper.toRepresentation(createdExaminationPeriod);
        URI uri = UriBuilder.fromResource(ExaminationPeriodResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }
}
