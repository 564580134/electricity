package com.electricity.serviceimpl.test;

import com.electricity.mapper.test.RichTextMapper;
import com.electricity.model.test.RichText;
import com.electricity.service.test.RichTextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: LiuRunYong
 * @Date: 2020/5/13
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RichTextServiceImpl implements RichTextService {

    final RichTextMapper richTextMapper;

    public RichTextServiceImpl(RichTextMapper richTextMapper) {
        this.richTextMapper = richTextMapper;

    }

    @Override
    public List<RichText> findRichTextByCondition(String RichTextTitle) {
        return richTextMapper.selectAll();
    }


    @Override
    public void insertRichText(RichText richText) {
        richTextMapper.insertSelective(richText);
    }

    @Override
    public void updateRichText(RichText richText) {
        richTextMapper.updateByPrimaryKey(richText);
    }

    @Override
    public void deleteRichText(Integer[] Ids) {
        for (Integer id : Ids) {
            richTextMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public RichText findById(Integer id) {
        return richTextMapper.selectByPrimaryKey(id);
    }
}
