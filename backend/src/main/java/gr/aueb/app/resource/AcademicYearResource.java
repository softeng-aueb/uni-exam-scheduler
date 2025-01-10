package gr.aueb.app.resource;

import gr.aueb.app.application.AcademicYearService;
import gr.aueb.app.domain.AcademicYear;
import gr.aueb.app.representation.AcademicYearMapper;
import gr.aueb.app.representation.AcademicYearRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

import static gr.aueb.app.resource.AppUri.ACADEMIC_YEARS;

@Path(ACADEMIC_YEARS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AcademicYearResource {
    @Inject
    AcademicYearService academicYearService;

    @Inject
    AcademicYearMapper academicYearMapper;

    @Context
    UriInfo uriInfo;

    @GET
    public List<AcademicYearRepresentation> findAll() {
        return academicYearMapper.toRepresentationList(academicYearService.findAll());
    }

    @POST
    public Response create(AcademicYearRepresentation representation) {
        // TODO check if toModel makes the validation we want
        AcademicYear newAcademicYear = academicYearMapper.toModel(representation);
        AcademicYear createdAcademicYear = academicYearService.create(newAcademicYear);
        AcademicYearRepresentation response = academicYearMapper.toRepresentation(createdAcademicYear);
        URI uri = UriBuilder.fromResource(AcademicYearResource.class).path(String.valueOf(response.id)).build();
        return Response.created(uri).entity(response).build();
    }

    @PUT
    @Path("/activate/{academicYearId}")
    public Response activate(@PathParam("academicYearId") Integer academicYearId) {
        academicYearService.setActive(academicYearId);
        return Response.noContent().build();
    }
}
