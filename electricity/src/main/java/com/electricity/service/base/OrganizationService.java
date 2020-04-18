package com.electricity.service.base;

import com.github.pagehelper.PageInfo;
import com.electricity.model.base.Organization;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: OrganizationService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public interface OrganizationService {

    /**
     * 判断组织是否存在
     *
     * @param organizationName 组织名称
     * @param organizationId   组织id
     * @return true: 存在 flase: 不存在
     */
    boolean isExist(String organizationName, String organizationId);

    /**
     * 查询组织根据条件
     *
     * @param organizationName 组织名称
     * @param userId           用户编号
     * @param pageNum          当前页
     * @param pageSize         每页显示条目数
     * @return
     */
    PageInfo<Organization> findOrganizations(String organizationName, Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 添加组织
     *
     * @param organization 组织实体类
     */
    void insertOrganization(Organization organization);

    /**
     * 更新组织
     *
     * @param organization 组织实体类
     */
    void updateOrganization(Organization organization);

    /**
     * 删除组织
     *
     * @param organizationIds 组织数组
     */
    void deleteOrganization(String[] organizationIds);


    /**
     * 查询当前用户组织
     *
     * @param example 条件数据集
     * @return list
     */
    List<Organization> selectOrganizationByExample(Example example);

    /**
     * 查询书否有下级
     *
     * @param example 条件
     * @return int
     */
    Integer findSubOrganizationById(Example example);
}
