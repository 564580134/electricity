package com.electricity.service.test;

import com.electricity.model.base.Role;
import com.electricity.model.test.RichText;

import java.util.List;

/**
 * @Description:
 * @Author: LiuRunYong
 * @Date: 2020/5/13
 **/
public interface RichTextService {

    /**
     * 查询富文本根据条件
     *
     * @param RichTextTitle 富文本标题
     * @return list
     */
    List<RichText> findRichTextByCondition(String RichTextTitle);


    /**
     * 添加富文本
     *
     * @param richText 富文本信息
     */
    void insertRichText(RichText richText);

    /**
     * 修改富文本
     *
     * @param richText 富文本信息
     */
    void updateRichText(RichText richText);

    /**
     * 删除富文本
     *
     * @param Ids 富文本Id数组
     */
    void deleteRichText(Integer[] Ids);

    /**
     * 获取单条
     *
     * @param id
     * @return
     */
    RichText findById(Integer id);
}
