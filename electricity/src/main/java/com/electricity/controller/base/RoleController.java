package com.electricity.controller.base;


import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.electricity.common.utils.JwtUtils;
import com.electricity.model.base.Role;
import com.electricity.model.base.User;
import com.electricity.service.base.RolePermissionService;
import com.electricity.service.base.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.electricity.common.annotation.RequiresPermissions;
import com.electricity.common.exception.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: RoleController
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Api(tags = "用户角色模块")
@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    public RoleController(RoleService roleService, RolePermissionService rolePermissionService) {
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
    }

    @ApiOperation(value = "查询所有角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleTitle", value = "角色标题", type = "string"),
            @ApiImplicitParam(name = "organizationId", value = "组织id", type = "string"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "0", type = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", defaultValue = "10", type = "string")
    })
    @GetMapping("/findRoleByCondition")
    @RequiresPermissions(permissions = {"system_manage:role_manage:select"})
    public ServerResponse findRoleByCondition(String roleTitle, String organizationId, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
            List<Role> roleList = roleService.findRoleByCondition(roleTitle, organizationId);
            PageInfo<Role> pageInfo = new PageInfo<>(roleList);
            return ServerResponse.createBySuccess("查询成功", pageInfo);
        }
        return ServerResponse.createBySuccess("查询成功", roleService.findRoleByCondition(roleTitle, organizationId));
    }


    @ApiOperation(value = "新增角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, type = "string"),
            @ApiImplicitParam(name = "roleTitle", value = "角色标题", required = true, type = "string"),
            @ApiImplicitParam(name = "description", value = "角色描述", required = true, type = "string")
    })
    @PostMapping("/insertRole")
    @RequiresPermissions(permissions = {"system_manage:role_manage:insert"})
    public ServerResponse insertRole(@RequestParam("roleName") String roleName,
                                     @RequestParam("roleTitle") String roleTitle,
                                     @RequestParam("description") String description) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName)
                .andEqualTo("roleTitle", roleTitle)
                .andEqualTo("description", description);

        if (roleService.isExist(example)) {
            return ServerResponse.createByErrorMessage("该角色已存在,无法新增");
        } else {
            roleService.insertRole(
                    new Role()
                            .setRoleName(roleName)
                            .setRoleTitle(roleTitle)
                            .setDescription(description));
            return ServerResponse.createBySuccessMessage("新增成功");
        }
    }

    @ApiOperation(value = "修改角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, type = "integer"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", required = true, type = "string"),
            @ApiImplicitParam(name = "roleTitle", value = "角色标题", required = true, type = "string"),
            @ApiImplicitParam(name = "description", value = "角色描述", required = true, type = "string"),
    })
    @PostMapping("/updateRole")
    @RequiresPermissions(permissions = {"system_manage:role_manage:update"})
    public ServerResponse updateRole(@RequestParam("roleId") Integer roleId,
                                     @RequestParam("roleName") String roleName,
                                     @RequestParam("roleTitle") String roleTitle,
                                     @RequestParam("description") String description) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName)
                .andEqualTo("roleTitle", roleTitle)
                .andEqualTo("description", description)
                .andNotEqualTo("roleId", roleId);
        if (roleService.isExist(example)) {
            return ServerResponse.createByErrorMessage("该角色已存在,无法修改");
        } else {
            roleService.updateRole(
                    new Role()
                            .setRoleId(roleId)
                            .setRoleName(roleName)
                            .setRoleTitle(roleTitle)
                            .setDescription(description));
            return ServerResponse.createBySuccessMessage("修改成功");
        }
    }

    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParam(name = "roleIds", value = "角色Id", required = true, type = "integer")
    @PostMapping("/deleteRole")
    @RequiresPermissions(permissions = {"system_manage:role_manage:delete"})
    public ServerResponse deleteRole(@RequestParam("roleIds") Integer[] roleIds) {
        roleService.deleteRole(roleIds);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @ApiOperation(value = "角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, type = "integer"),
            @ApiImplicitParam(name = "permissionIds", value = "权限Id", required = true, type = "integer"),
    })
    @PostMapping("/insertRolePermission")
    @RequiresPermissions(permissions = "system_manage:role_manage:role_permission")
    public ServerResponse insertRolePermission(@RequestParam("roleId") Integer roleId,
                                               @RequestParam("permissionIds") Integer[] permissionIds) {
        rolePermissionService.insertRolePermission(roleId, permissionIds);
        return ServerResponse.createBySuccessMessage("新增角色权限成功");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/selectAll")
    public ServerResponse selectRoleAll() {
        return ServerResponse.createBySuccess(roleService.selectRoleAll());
    }


}
