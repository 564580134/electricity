package com.electricity.service.base;

import java.util.List;
import java.util.Map;

/**
 * @Description: UserType
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface UserType {

    /**
     * 创建用户
     *
     * @param readAll        Execl 导入的数据
     * @param gradeId        年级 id
     * @param classId        班级 id
     * @param roleIds        角色 id 数组
     * @param organizationId 组织 id
     */
    void createUser(List<Map<String, Object>> readAll, Integer gradeId, Integer classId, Integer[] roleIds, String organizationId);
}
