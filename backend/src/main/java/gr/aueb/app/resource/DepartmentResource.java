package gr.aueb.app.resource;

import gr.aueb.app.application.DepartmentService;
import gr.aueb.app.representation.DepartmentMapper;
import gr.aueb.app.representation.DepartmentRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

import static gr.aueb.app.resource.AppUri.DEPARTMENTS;

@Path(DEPARTMENTS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class DepartmentResource {

    @Inject
    DepartmentMapper departmentMapper;

    @Inject
    DepartmentService departmentService;

    @Context
    UriInfo uriInfo;

    @GET
    public List<DepartmentRepresentation> findAll() {
        return departmentMapper.toRepresentationList(departmentService.findAll());
    }
}
