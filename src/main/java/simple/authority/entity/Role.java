package simple.authority.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import simple.authority.entity.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@Data
public class Role extends AbstractEntity {

    @ApiModelProperty(value = "角色编码")
    private String code;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ManyToMany
    private List<Resource> resources = new ArrayList();
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList();
}
