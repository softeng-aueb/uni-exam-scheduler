package gr.aueb.app.resource;

import gr.aueb.app.application.SupervisionService;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.representation.SupervisionMapper;
import gr.aueb.app.representation.SupervisionRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
