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

package simple.authority.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@ToString
public class AbstractEntity {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;

    @CreatedDate
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createdDate;

    @LastModifiedDate
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date lastModifiedDate;

    @CreatedBy
    @ApiModelProperty(hidden = true)
    private String createdBy;

    @LastModifiedBy
    @ApiModelProperty(hidden = true)
    private String lastModifiedBy;

    @ApiModelProperty(allowableValues = "true,false", example = "true")
    private Boolean isAvailable;

    @ApiModelProperty(hidden = true)
    public String getCreatedDateString() {
        return sdf.format(createdDate);
    }

    @ApiModelProperty(hidden = true)
    public String getLastModifiedDateString() {
        return sdf.format(lastModifiedDate);
    }
}
