package gr.aueb.app.resource;

import gr.aueb.app.application.SolutionService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import static gr.aueb.app.resource.AppUri.SOLVE;

@Path(SOLVE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SolutionResource {
    @Inject
    SolutionService solutionService;

    @Context
    UriInfo uriInfo;

    @POST
    @Path("/{examinationPeriodId}")
    public Response solve(@PathParam("examinationPeriodId") Integer examinationPeriodId) {
        solutionService.solve(examinationPeriodId);
        //return supervisionMapper.toRepresentationList(supervisions);
        return Response.ok().build();
    }
}

