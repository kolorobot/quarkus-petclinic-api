package pl.codeleak.samples.petclinic.api;

import pl.codeleak.samples.petclinic.api.support.PageRequest;
import pl.codeleak.samples.petclinic.api.support.ResourceSupport;
import pl.codeleak.samples.petclinic.model.Visit;
import pl.codeleak.samples.petclinic.repository.VisitRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(VisitResource.RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class VisitResource {

    public static final String RESOURCE_PATH = "/visits";

    @Context
    UriInfo urlInfo;

    @Inject
    VisitRepository visitRepository;

    @GET
    public Response getAll(@BeanParam PageRequest pageRequest) {
        return ResourceSupport.getAll(pageRequest, visitRepository);
    }

    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") Long id) {
        return ResourceSupport.getOne(id, visitRepository);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid Visit visit) {
        return ResourceSupport.create(visit, visitRepository, urlInfo);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        var vet = visitRepository.findByIdOptional(id);
        vet.ifPresent(v -> visitRepository.delete(v));
        return Response.noContent().build();
    }

}
