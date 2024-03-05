package gr.aueb.app.resource;

import gr.aueb.app.application.ClassroomService;
import gr.aueb.app.domain.Classroom;
import gr.aueb.app.representation.ClassroomMapper;
import gr.aueb.app.representation.ClassroomRepresentation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.CLASSROOMS;

@Path(CLASSROOMS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ClassroomResource {
    @Inject
    ClassroomService classroomService;

    @Inject
    ClassroomMapper classroomMapper;


    @Context
    UriInfo uriInfo;

    @GET
    public List<ClassroomRepresentation> findAll() {
        return classroomMapper.toRepresentationList(classroomService.findAll());
    }

    @GET
    @Path("/{classroomId}")
    public ClassroomRepresentation findOne(@PathParam("classroomId") Integer classroomId) {
        Classroom foundClassroom = classroomService.findOne(classroomId);
        return classroomMapper.toRepresentation(foundClassroom);
    }


    @POST
    public Response create(ClassroomRepresentation representation) {
        // TODO check if toModel makes the validation we want
        Classroom newClassroom = classroomMapper.toModel(representation);
        Classroom createdClassroom = classroomService.create(newClassroom);
        ClassroomRepresentation response = classroomMapper.toRepresentation(createdClassroom);
        URI uri = UriBuilder.fromResource(ClassroomResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }

    @PUT
    @Path("/{classroomId}")
    public Response update(@PathParam("classroomId") Integer classroomId, ClassroomRepresentation representation) {
        Classroom updateClassroom = classroomMapper.toModel(representation);
        classroomService.update(classroomId, updateClassroom);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{classroomId}")
    public Response delete(@PathParam("classroomId") Integer classroomId) {
        classroomService.delete(classroomId);
        return Response.noContent().build();
    }
}
