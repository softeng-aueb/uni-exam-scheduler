package gr.aueb.app.resource;

import gr.aueb.app.application.SupervisorService;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.representation.SupervisorMapper;
import gr.aueb.app.representation.SupervisorRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.SUPERVISORS;

@Path(SUPERVISORS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SupervisorResource {

    @Inject
    SupervisorService supervisorService;

    @Inject
    SupervisorMapper supervisorMapper;


    @Context
    UriInfo uriInfo;

    @GET
    @Transactional
    public List<SupervisorRepresentation> finnAll() {
        return supervisorMapper.toRepresentationList(supervisorService.findAll());
    }

    @GET
    @Path("/{supervisorId}")
    @Transactional
    public SupervisorRepresentation find(@PathParam("supervisorId") Integer supervisorId) {
        try {
            Supervisor foundSupervisor = supervisorService.findOne(supervisorId);
            if(foundSupervisor == null) {
                throw new NotFoundException();
            }
            return supervisorMapper.toRepresentation(foundSupervisor);
        } catch (Exception e) {
            throw e;
        }
    }


    @POST
    @Transactional
    public Response create(SupervisorRepresentation representation) {
        // TODO check if toModel makes the validation we want
        try {
            Supervisor createdSupervisor = supervisorService.create(representation);
            URI uri = UriBuilder.fromResource(SupervisorResource.class).path(String.valueOf(createdSupervisor.getId())).build();
            return Response.created(uri).entity(supervisorMapper.toRepresentation(createdSupervisor)).build();
        } catch(Exception e) {
            throw new BadRequestException();
        }
    }

}
