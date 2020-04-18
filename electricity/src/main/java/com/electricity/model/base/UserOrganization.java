package com.electricity.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description: UserOrganization
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "user_organization")
public class UserOrganization implements Serializable {

    /**
     * 用户组织id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userOrganizationId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组织id
     */
    private Integer organizationId;
}
