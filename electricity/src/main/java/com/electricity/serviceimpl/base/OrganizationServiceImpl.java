package com.electricity.serviceimpl.base;


import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.OrganizationMapper;
import com.electricity.mapper.base.RoleMapper;
import com.electricity.model.base.Organization;
import com.electricity.model.base.Role;
import com.electricity.service.base.OrganizationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: OrganizationServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private RoleMapper roleMapper;



    /**
     * 定义数字大小常量
     */
    private static final Integer NUMBER_SIZE = 10;

    public Organization findOrganizationByExample(Example example) {
        return organizationMapper.selectOneByExample(example);
    }

    @Override
    public PageInfo<Organization> findOrganizations(String organizationName, Integer userId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Organization> organizationList = organizationMapper.findOrganizations(organizationName, userId, null);
        // 获取分页数据
        PageInfo<Organization> pageInfo = new PageInfo<>(organizationList);
        // 返回分页数据
        return pageInfo;
    }

    public Integer findOrganizationCount(Example example) {
        return organizationMapper.selectCountByExample(example);
    }

    @Override
    public boolean isExist(String organizationName, String organizationId) {
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("organizationName", organizationName);
        Organization organization = findOrganizationByExample(example);
        // true: 存在 flase: 不存在
        return organization != null;
    }

    @Override
    public void insertOrganization(Organization organization) {
        Example example = new Example(Organization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("superiorId", organization.getSuperiorId());
        Integer organizationCount = findOrganizationCount(example);
        int afterInt = organizationCount + 1;

        if (afterInt < NUMBER_SIZE) {
            organization.setOrganizationId(organization.getSuperiorId() + "0" + afterInt);
        } else {
            organization.setOrganizationId(organization.getSuperiorId() + afterInt);
        }
        // 新增组织
        int result = organizationMapper.insertSelective(organization);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
        }
    }

    @Override
    public void updateOrganization(Organization organization) {
        int result = organizationMapper.updateByPrimaryKeySelective(organization);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_UPDATE_EXCEPTION);
        }
    }

    @Override
    public void deleteOrganization(String[] organizationIds) {
        for (String organizationId : organizationIds) {
            int organizationResult = organizationMapper.deleteByPrimaryKey(organizationId);
            if (organizationResult == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_DELETE_EXCEPTION);
            } else {
                Example example = new Example(Role.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("organizationId", organizationId);
                roleMapper.deleteByExample(example);
            }
        }
    }

    @Override
    public List<Organization> selectOrganizationByExample(Example example) {
        return organizationMapper.selectByExample(example);
    }

    @Override
    public Integer findSubOrganizationById(Example example) {
      return organizationMapper.selectCountByExample(example);
    }
}
