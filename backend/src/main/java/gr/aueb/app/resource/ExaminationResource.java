package gr.aueb.app.resource;

import gr.aueb.app.application.ExaminationService;
import gr.aueb.app.domain.Supervision;
import gr.aueb.app.representation.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @Path("/{examinationPeriodId}")
    @Transactional
    public List<ExaminationRepresentation> finnAllInSamePeriod(@PathParam("examinationPeriodId") Integer examinationPeriodId) {
        return examinationMapper.toRepresentationList(examinationService.findAllInSamePeriod(examinationPeriodId));
    }

    @POST
    @Path("/{examinationId}/supervisions")
    @Transactional
    public Response addSupervision(@PathParam("examinationId") Integer examinationId, Integer supervisorId) {
        // TODO check if toModel makes the validation we want
        try {
            Supervision addedSupervision = examinationService.addSupervision(examinationId, supervisorId);
            URI uri = UriBuilder.fromResource(ExaminationResource.class).path(String.valueOf(addedSupervision.getExamination().getId())).build();
            return Response.ok(uri).entity(supervisionMapper.toRepresentation(addedSupervision)).build();
        } catch(Exception e) {
            throw new BadRequestException();
        }
    }

    @DELETE
    @Path("/{examinationId}/supervisions/{supervisionId}")
    @Transactional
    public Response removeSupervision(@PathParam("examinationId") Integer examinationId,
                                      @PathParam("supervisionId") Integer supevisionId) {
        examinationService.removeSupervision(examinationId, supevisionId);
        return Response.noContent().build();
    }
}
