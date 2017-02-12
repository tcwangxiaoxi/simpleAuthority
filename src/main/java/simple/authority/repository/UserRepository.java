package simple.authority.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import simple.authority.entity.Role;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<Role, Long> {
}
