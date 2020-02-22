package pl.codeleak.samples.petclinic.api.support;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import pl.codeleak.samples.petclinic.model.BaseEntity;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.function.Function;

public final class ResourceSupport {

    public static Response getAll(PageRequest pageRequest, PanacheRepository repository) {
        return Response.ok(repository.findAll()
                        .page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize()))
                        .list()).build();
    }

    public static Response getOne(Long id, PanacheRepository repository) {
       return getItem(id, repository, Function.identity());
    }

    public static Response getItem(Long id, PanacheRepository repository, Function itemExtractor) {
        var optional = repository.findByIdOptional(id);
        if (optional.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(itemExtractor.apply(optional.get())).build();
    }

    public static <T extends BaseEntity> Response create(T entity, PanacheRepository<T> repository, UriInfo uriInfo) {

        repository.persist(entity);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path("{id}")
                .resolveTemplate("id", entity.getId())
                .build();

        return Response.created(location).build();
    }

    public static <T extends BaseEntity> Response update(T entity, PanacheRepository<T> repository, UriInfo uriInfo) {
        repository.persist(entity);
        return Response.ok(entity).build();
    }
}
