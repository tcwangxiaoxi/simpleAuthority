package simple.authority.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import simple.authority.entity.base.AbstractEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@Data
public class Resource extends AbstractEntity {

    @ApiModelProperty(value = "资源编码")
    private String code;
    @ApiModelProperty(value = "资源名称")
    private String name;
    @ApiModelProperty(value = "资源操作方式")
    private String action;
    @ApiModelProperty(value = "资源路径")
    private String url;

    @ManyToMany(mappedBy = "resources")
    private Set<Role> roles = new HashSet<>();
}
