package com.electricity.model.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description: Organization
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "organization_permission")
public class OrganizationPermission  implements Serializable {

    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @ApiModelProperty(value = "组织编号")
    private String organizationId;

    @ApiModelProperty(value = "权限编号")
    private Integer permissionId;

}
