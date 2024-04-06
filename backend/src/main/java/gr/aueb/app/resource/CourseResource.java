package gr.aueb.app.resource;

import gr.aueb.app.application.CourseService;
import gr.aueb.app.domain.Course;
import gr.aueb.app.representation.CourseMapper;
import gr.aueb.app.representation.CourseRepresentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.COURSES;

@Path(COURSES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CourseResource {
    @Inject
    CourseService courseService;

    @Inject
    CourseMapper courseMapper;


    @Context
    UriInfo uriInfo;

    @GET
    public List<CourseRepresentation> findAll() {
        return courseMapper.toRepresentationList(courseService.findAll());
    }

    @GET
    @Path("/{courseId}")
    public CourseRepresentation findOne(@PathParam("courseId") Integer courseId) {
        Course foundCourse = courseService.findOne(courseId);
        return courseMapper.toRepresentation(foundCourse);
    }


    @POST
    public Response create(CourseRepresentation representation) {
        // TODO check if toModel makes the validation we want
        Course newCourse = courseMapper.toModel(representation);
        Course createdCourse = courseService.create(newCourse);
        CourseRepresentation response = courseMapper.toRepresentation(createdCourse);
        URI uri = UriBuilder.fromResource(ClassroomResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }

    @PUT
    @Path("/{courseId}")
    public Response update(@PathParam("courseId") Integer courseId, CourseRepresentation representation) {
        Course updateCourse = courseMapper.toModel(representation);
        courseService.update(courseId, updateCourse);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{courseId}")
    public Response delete(@PathParam("courseId") Integer courseId) {
        courseService.delete(courseId);
        return Response.noContent().build();
    }
}
