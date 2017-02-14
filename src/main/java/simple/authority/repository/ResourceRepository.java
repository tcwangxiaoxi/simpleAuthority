package simple.authority.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import simple.authority.entity.Resource;

@RepositoryRestResource
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {

    Resource findByCode(String code);

    Page<Resource> findAllByName(String name, Pageable pageable);
}
