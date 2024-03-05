package gr.aueb.app.resource;

import gr.aueb.app.application.ExaminationService;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.representation.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

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

    @Context
    UriInfo uriInfo;

    @GET
    public List<ExaminationRepresentation> findAllInSamePeriod(@QueryParam("examinationPeriod") Integer examinationPeriodId) {
        return examinationMapper.toRepresentationList(examinationService.findAllInSamePeriod(examinationPeriodId));
    }

    @POST
    @Path("/{examinationId}/supervisions")
    public Response addSupervision(@PathParam("examinationId") Integer examinationId, SupervisorRepresentation representation) {
        // TODO check if toModel makes the validation we want
        Supervision addedSupervision = examinationService.addSupervision(examinationId, representation.id);
        SupervisionRepresentation response = supervisionMapper.toRepresentation(addedSupervision);
        URI uri = UriBuilder.fromResource(ExaminationResource.class).path(String.valueOf(response.examination.id)).build();
        return Response.created(uri).entity(addedSupervision).build();
    }

    @DELETE
    @Path("/{examinationId}/supervisions/{supervisionId}")
    public Response removeSupervision(@PathParam("examinationId") Integer examinationId,
                                      @PathParam("supervisionId") Integer supervisionId) {
        examinationService.removeSupervision(examinationId, supervisionId);
        return Response.noContent().build();
    }
}
