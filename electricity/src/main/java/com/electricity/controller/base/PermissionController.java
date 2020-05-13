package com.electricity.controller.base;

import com.electricity.model.base.Permission;
import com.electricity.model.base.User;
import com.electricity.service.base.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.electricity.common.annotation.RequiresPermissions;
import com.electricity.common.exception.ServerResponse;
import com.electricity.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: PermissionController
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Api(tags = "用户权限模块")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "查询所有权限信息")
    @GetMapping("/findPermissionAll")
    @RequiresPermissions(permissions = {
            "system_manage:permission_manage:permission_manage"})
    public ServerResponse<List<Permission>> findPermissionAll() {
        return ServerResponse.createBySuccess("查询成功", permissionService.findPermissionAll());
    }

    @ApiOperation(value = "根据类型查询权限信息")
    @ApiImplicitParam(name = "permissionType", value = "权限类型", required = true, type = "integer")
    @GetMapping("/findPermissionByType")
    public ServerResponse<List<Permission>> findPermissionByType(@RequestParam("permissionType") Integer permissionType) {
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("permissionType", permissionType);
        return ServerResponse.createBySuccess("查询成功", permissionService.findPermissionByExample(example));
    }

    @ApiOperation(value = "根据条件查询权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permissionName", value = "权限名称", type = "string"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", type = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", type = "string"),
            @ApiImplicitParam(name = "token", value = "token", type = "string")
    })
    @GetMapping("/findPermissionByCondition")
    @RequiresPermissions(permissions = {
            "system_manage:permission_manage:select"})
    public ServerResponse findPermissionByCondition(@RequestParam(value = "permissionName", required = false) String permissionName,
                                                    @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                    @RequestParam(value = "token") String token) {
        if (token == null) {
            return ServerResponse.createByErrorMessage("未获取到token");
        }
        User currentUser = JwtUtils.unsign(token, User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("请先登录");
        }
        if (pageNum != null && pageSize != null) {
            // 设置分页
            PageHelper.startPage(pageNum, pageSize);
            // 查询数据
            List<Permission> list = permissionService.findPermission(permissionName, currentUser.getUserId());
            // 获取分页数据
            PageInfo<Permission> pageInfo = new PageInfo<>(list);
            // 返回分页数据
            return ServerResponse.createBySuccess(pageInfo);
        } else {
            return ServerResponse.createBySuccess(permissionService.findPermission(permissionName, currentUser.getUserId()));
        }
    }

    @ApiOperation(value = "添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "permissionName", value = "权限名称", required = true, type = "string"),
            @ApiImplicitParam(name = "permissionValue", value = "权限值", required = true, type = "string"),
            @ApiImplicitParam(name = "permissionType", value = "权限类型 1:目录 2:菜单 3:按钮", required = true, type = "integer"),
            @ApiImplicitParam(name = "permissionStatus", value = "权限状态 0:禁止 1:正常", required = true, type = "integer"),
            @ApiImplicitParam(name = "superiorId", value = "上级id", type = "integer")
    })
    @PostMapping("/insertPermission")
    @RequiresPermissions(permissions = {"system_manage:permission_manage:insert"})
    public ServerResponse insertPermission(@RequestParam("permissionName") String permissionName,
                                           @RequestParam("permissionValue") String permissionValue,
                                           @RequestParam("permissionType") Integer permissionType,
                                           @RequestParam("permissionStatus") Integer permissionStatus,
                                           @RequestParam(value = "superiorId", defaultValue = "0") Integer superiorId) {
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("permissionName", permissionName)
                .andEqualTo("permissionValue", permissionValue)
                .andEqualTo("permissionType", permissionType);
        if (permissionService.isExist(example)) {
            return ServerResponse.createByErrorMessage("添加权限信息重复");
        }

        // 添加权限
        permissionService.insertPermission(
                new Permission()
                        .setPermissionName(permissionName)
                        .setPermissionValue(permissionValue)
                        .setPermissionType(permissionType)
                        .setPermissionStatus(permissionStatus)
                        .setSuperiorId(superiorId));
        return ServerResponse.createBySuccessMessage("添加权限成功");
    }

    @ApiOperation(value = "修改权限")
    @PostMapping("/updatePermission")
    @RequiresPermissions(permissions = {"system_manage:permission_manage:update"})
    public ServerResponse updatePermission(@ModelAttribute Permission permission) {
        Example example = new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("permissionName", permission.getPermissionName())
                .andEqualTo("permissionValue", permission.getPermissionValue())
                .andEqualTo("permissionType", permission.getPermissionType())
                .andNotEqualTo("permissionId", permission.getPermissionId());
        if (permissionService.isExist(example)) {
            return ServerResponse.createByErrorMessage("修改权限信息重复");
        }

        // 修改权限
        permissionService.updatePermission(permission);
        return ServerResponse.createBySuccessMessage("修改权限成功");
    }

    @ApiOperation(value = "删除权限")
    @RequiresPermissions(permissions = {"system_manage:permission_manage:delete"})
    @ApiImplicitParam(name = "permissionIds", value = "权限id", required = true, type = "integer")
    @PostMapping("/deletePermission")
    public ServerResponse deletePermission(@RequestParam("permissionIds") Integer[] permissionIds) {
        // 删除权限
        permissionService.deletePermission(permissionIds);
        return ServerResponse.createBySuccessMessage("删除权限成功");
    }


    @ApiOperation(value = "根据角色查询权限")
    @GetMapping("/findPermissionByRoleId")
    public ServerResponse findPermissionByRoleId(Integer roleId) {
        return ServerResponse.createBySuccess(permissionService.findPermissionByRoleId(roleId));
    }


    @ApiOperation(value = "获取权限树")
    @GetMapping("/permissionZTree")
    public ServerResponse permissionZTree() {
        return ServerResponse.createBySuccess(permissionService.permissionZTree());
    }

}
