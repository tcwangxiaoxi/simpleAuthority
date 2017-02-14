package simple.authority.entity.base;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@ToString
public class AbstractPageableEntity extends AbstractEntity {

    private PageRequest page;

}
