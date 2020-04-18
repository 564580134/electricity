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
 * @Description: UserPermission
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "user_permission")
public class UserPermission implements Serializable {

    /**
     * 用户权限id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userPermissionId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 权限id
     */
    private Integer permissionId;
}
