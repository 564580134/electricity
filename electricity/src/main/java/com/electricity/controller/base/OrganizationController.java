package com.electricity.controller.base;


import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.electricity.model.base.Organization;
import com.electricity.model.base.User;
import com.electricity.service.base.OrganizationPermissionService;
import com.electricity.service.base.OrganizationService;
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
 * @Description: OrganizationController
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Api(tags = "组织模块接口")
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationPermissionService organizationPermissionService;


    @ApiOperation(value = "添加组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationName", value = "组织名称", required = true, type = "string"),
            @ApiImplicitParam(name = "description", value = "组织描述", required = true, type = "string"),
            @ApiImplicitParam(name = "superiorId", value = "上级id", required = true, type = "string"),
    })
    @PostMapping("/insertOrganization")
    @RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage:insert"})
    public ServerResponse insertOrganization(@RequestParam("organizationName") String organizationName,
                                             @RequestParam("description") String description,
                                             @RequestParam(value = "superiorId", defaultValue = "00") String superiorId) {
        if (organizationService.isExist(organizationName, null)) {
            return ServerResponse.createByErrorMessage("该组织名称已存在,请重新注册");
        }
        if (superiorId == null) {
            return ServerResponse.createByErrorMessage("请选择上级组织");
        }

        organizationService.insertOrganization(
                new Organization()
                        .setOrganizationName(organizationName)
                        .setDescription(description)
                        .setSuperiorId(superiorId)
        );
        return ServerResponse.createBySuccessMessage("新增组织成功");
    }

    @ApiOperation(value = "更新组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", value = "组织Id", required = true, type = "string"),
            @ApiImplicitParam(name = "organizationName", value = "组织名称", required = true, type = "string"),
            @ApiImplicitParam(name = "description", value = "组织描述", required = true, type = "string"),
            @ApiImplicitParam(name = "superiorId", value = "上级id", required = true, type = "string"),
    })
    @PostMapping("/updateOrganization")
    @RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage:update"})
    public ServerResponse updateOrganization(@RequestParam("organizationId") String organizationId,
                                             @RequestParam("organizationName") String organizationName,
                                             @RequestParam("description") String description,
                                             @RequestParam("superiorId") String superiorId) {
        if (organizationService.isExist(organizationName, organizationId)) {
            return ServerResponse.createByErrorMessage("该组织名称已存在,请重新注册");
        }
        organizationService.updateOrganization(
                new Organization()
                        .setOrganizationId(organizationId)
                        .setOrganizationName(organizationName)
                        .setDescription(description)
                        .setSuperiorId(superiorId)
        );
        return ServerResponse.createBySuccessMessage("更新组织成功");
    }

    @ApiOperation(value = "删除组织")
    @ApiImplicitParam(name = "organizationIds", value = "组织Id", required = true, type = "string")
    @PostMapping("/deleteOrganization")
    @RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage:delete"})
    public ServerResponse deleteOrganization(@RequestParam("organizationIds") String[] organizationIds) {
        if (organizationIds==null) {
            ServerResponse.createByError("请选择删除对象");
        }else if(organizationIds.length==0){
            ServerResponse.createByError("请选择删除对象");
        }
        organizationService.deleteOrganization(organizationIds);
        return ServerResponse.createBySuccessMessage("删除组织成功");
    }


    @ApiOperation(value = "新增组织权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", value = "组织Id", required = true, type = "string"),
            @ApiImplicitParam(name = "permissionIds", value = "权限Id", required = true, type = "integer"),
    })
    @PostMapping("/insertOrganizationPermission")
    //@RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage:delete"})
    public ServerResponse insertRolePermission(@RequestParam("organizationId") String organizationId,
                                               @RequestParam("permissionIds") Integer[] permissionIds) {
        organizationPermissionService.insertRolePermission(organizationId, permissionIds);
        return ServerResponse.createBySuccessMessage("新增组织权限成功");
    }

    @ApiOperation(value = "根据组织权编号查询权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", value = "组织Id", required = true, type = "string")
    })
    @PostMapping("/selectOrgPermissionByOrgId")
    public ServerResponse selectOrgPermissionByOrgId(@RequestParam("organizationId") String organizationId) {
        // 验证参数
        if (StringUtils.isBlank(organizationId)) {
            return ServerResponse.createByError("参数异常");
        }
        return ServerResponse.createBySuccess(organizationPermissionService.selectOrgPermissionByOrgId(organizationId));
    }


    @ApiOperation(value = "查找组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationName", value = "组织名称", paramType = "string"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", defaultValue = "0", paramType = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", defaultValue = "10", paramType = "string"),
            @ApiImplicitParam(name = "token", value = "当前用户", paramType = "integer")
    })
    @GetMapping("/list")
    @RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage"})
    public ServerResponse list(@RequestParam(value = "organizationName", required = false) String organizationName,
                               @RequestParam(value = "pageNum", required = false) Integer pageNum,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "token") String token) {
        // 验证参数
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByError("请先登录");
        }
        // 获取当前用户
        User currentUser = JwtUtils.unsign(token, User.class);
        if (currentUser == null) {
            return ServerResponse.createByError("请先登录");
        }
        // 设置查询参数
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isBlank(organizationName)) {
            criteria.andLike("organizationName", organizationName + "%");
        }
        // 设置分页
        if (pageNum != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        // 角色查询
        if (currentUser.getRoles().get(0).getRoleId() != 2) {
            criteria.andLike("organizationId", currentUser.getOrganizationId() + "%");
        } else {
            criteria.andLike("organizationId", null);
        }
        return ServerResponse.createBySuccess(new PageInfo<Organization>(organizationService.selectOrganizationByExample(example)));

    }

    @ApiOperation(value = "查询是否有下级")
    @ApiImplicitParam(name = "organizationId", value = "组织编号", paramType = "String[]")
    @GetMapping("/findSubOrganizationById")
    public ServerResponse findSubOrganizationById(@RequestParam(value = "organizationId") String[] organizationId) {
        // 验证
        if (organizationId == null) {
            return ServerResponse.createByError("参数异常");
        }
        for (String s : organizationId) {
            // 查询
            Example example = new Example(Organization.class);
            example.createCriteria().andLike("superiorId", s + "%");
            int rows = organizationService.findSubOrganizationById(example);
            if (rows > 1) {
                return ServerResponse.createByError();
            }
        }
        return ServerResponse.createBySuccess();
    }


    @ApiOperation(value = "返回组织树")
    @ApiImplicitParam(name = "token", value = "当前用户", paramType = "integer")
    @GetMapping("/tree")
    @RequiresPermissions(permissions = {"system_manage:organization_manage:organization_manage"})
    public ServerResponse tree(@RequestParam(value = "token") String token) {
        // 验证参数
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByError("请先登录");
        }
        // 获取当前用户
        User currentUser = JwtUtils.unsign(token, User.class);
        if (currentUser == null) {
            return ServerResponse.createByError("请先登录");
        }
        // 设置查询参数
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("organization_id");
        // 设置分页

        // 角色查询
        if (currentUser.getRoles().get(0).getRoleId() != 2) {
            criteria.andLike("organizationId", currentUser.getOrganizationId() + "%");
        } else {
            criteria.andLike("organizationId", null);
        }
        List<Organization> list = organizationService.selectOrganizationByExample(example);
        list.get(0).setSuperiorId("");
        return ServerResponse.createBySuccess(list);

    }
}
