package simple.authority.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import simple.authority.entity.Resource;

@RepositoryRestResource
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
}
