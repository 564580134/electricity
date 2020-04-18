package com.electricity.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: Organization
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "organization")
public class Organization implements Serializable {

    /**
     * 组织id
     */
    @Id
    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 组织描述
     */
    private String description;

    /**
     * 上级id
     */
    private String superiorId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date updateTime;
}
