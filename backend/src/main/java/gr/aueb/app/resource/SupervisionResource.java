package gr.aueb.app.resource;

import gr.aueb.app.application.SupervisionService;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.representation.SupervisionMapper;
import gr.aueb.app.representation.SupervisionRepresentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static gr.aueb.app.resource.AppUri.SUPERVISIONS;

@Path(SUPERVISIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SupervisionResource {

    @Inject
    SupervisionService supervisionService;

    @Inject
    SupervisionMapper supervisionMapper;

    @GET
    @Path("/{supervisionId}")
    public SupervisionRepresentation findOne(@PathParam("supervisionId") Integer supervisionId) {
        Supervision foundSupervision = supervisionService.findOne(supervisionId);
        return supervisionMapper.toRepresentation(foundSupervision);
    }

    @PUT
    @Path("/{supervisionId}/changePresent")
    public Response changePresentStatus(@PathParam("supervisionId") Integer supervisionId, SupervisionRepresentation representation) {
        supervisionService.changePresentStatus(supervisionId, representation.isPresent);
        return Response.noContent().build();
    }

    @DELETE
    public Response deleteAllInSamePeriod(@QueryParam("examinationPeriodId") Integer examinationPeriodId) {
        supervisionService.deleteAllInSamePeriod(examinationPeriodId);
        return Response.noContent().build();
    }
}
