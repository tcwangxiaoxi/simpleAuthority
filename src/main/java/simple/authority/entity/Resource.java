// ======================================================================
//
//      Copyright (C) 北京国双科技有限公司
//                    http://www.gridsum.com
//
//      保密性声明：此文件属北京国双科技有限公司所有，仅限拥有由国双科技
//      授予了相应权限的人所查看和所修改。如果你没有被国双科技授予相应的
//      权限而得到此文件，请删除此文件。未得国双科技同意，不得查看、修改、
//      散播此文件。
//
//
// ======================================================================

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
    @ApiModelProperty(hidden = true)
    private Set<Role> roles = new HashSet<>();
}
