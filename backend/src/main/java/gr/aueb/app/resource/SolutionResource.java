package gr.aueb.app.resource;

import gr.aueb.app.application.BenchmarkService;
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

    @Inject
    BenchmarkService benchmarkService;

    @Context
    UriInfo uriInfo;

    @POST
    @Path("/{examinationPeriodId}")
    public Response solve(@PathParam("examinationPeriodId") Integer examinationPeriodId) {
        solutionService.solve(examinationPeriodId);
        //return supervisionMapper.toRepresentationList(supervisions);
        return Response.ok().build();
    }

    @GET
    @Path("/benchmark/{examinationPeriodId}")
    public Response benchmark(@PathParam("examinationPeriodId") Integer examinationPeriodId) {
        benchmarkService.runBenchmark(examinationPeriodId);
        //return supervisionMapper.toRepresentationList(supervisions);
        return Response.ok().build();
    }
}

