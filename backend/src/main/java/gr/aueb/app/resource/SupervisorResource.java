package gr.aueb.app.resource;

import gr.aueb.app.application.SupervisorService;
import gr.aueb.app.domain.Supervisor;
import gr.aueb.app.representation.SupervisorMapper;
import gr.aueb.app.representation.SupervisorRepresentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

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
    public List<SupervisorRepresentation> findAll() {
        return supervisorMapper.toRepresentationList(supervisorService.findAll());
    }

    @GET
    @Path("/{supervisorId}")
    public SupervisorRepresentation findOne(@PathParam("supervisorId") Integer supervisorId) {
        Supervisor foundSupervisor = supervisorService.findOne(supervisorId);
        return supervisorMapper.toRepresentation(foundSupervisor);
    }


    @POST
    public Response create(SupervisorRepresentation representation) {
        // TODO check if toModel makes the validation we want
        Supervisor newSupervisor = supervisorMapper.toModel(representation);
        Supervisor createdSupervisor = supervisorService.create(newSupervisor, newSupervisor.getDepartment().getId());
        SupervisorRepresentation response = supervisorMapper.toRepresentation(createdSupervisor);
        URI uri = UriBuilder.fromResource(SupervisorResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }

    @PUT
    @Path("/{supervisorId}")
    public Response update(@PathParam("supervisorId") Integer supervisorId, SupervisorRepresentation representation) {
        Supervisor updateSupervisor = supervisorMapper.toModel(representation);
        supervisorService.update(supervisorId, updateSupervisor);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{supervisorId}")
    public Response delete(@PathParam("supervisorId") Integer supervisorId) {
        supervisorService.delete(supervisorId);
        return Response.noContent().build();
    }
}
