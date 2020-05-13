package com.electricity.controller.test;

import com.electricity.common.annotation.RequiresPermissions;
import com.electricity.common.exception.ServerResponse;
import com.electricity.model.base.Role;
import com.electricity.model.test.RichText;
import com.electricity.service.test.RichTextService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description:
 * @Author: LiuRunYong
 * @Date: 2020/5/13
 **/
@Api(tags = "富文模块")
@RestController
@RequestMapping("/rich/text/")
public class RichTextController {

    final RichTextService richTextService;

    public RichTextController(RichTextService richTextService) {
        this.richTextService = richTextService;
    }


    @ApiOperation(value = "查询所有富文本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "richTextTitle", value = "标题", type = "string"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "0", type = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", defaultValue = "10", type = "string")
    })
    @GetMapping("/findRichTextByCondition")
    public ServerResponse findRichTextByCondition(String richTextTitle, Integer pageNum, Integer pageSize) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
            List<RichText> richText = richTextService.findRichTextByCondition(richTextTitle);
            PageInfo<RichText> pageInfo = new PageInfo<>(richText);
            return ServerResponse.createBySuccess("查询成功", pageInfo);
        }
        return ServerResponse.createBySuccess("查询成功", richTextService.findRichTextByCondition(richTextTitle));
    }


    @ApiOperation(value = "新增富文本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "richTextTitle", value = "标题", required = true, type = "string"),
            @ApiImplicitParam(name = "richTextDescription", value = "描述", required = true, type = "string"),
            @ApiImplicitParam(name = "richTextContent", value = "内容", required = true, type = "string")
    })
    @PostMapping("/insertRichText")
    public ServerResponse insertRichText(@RequestParam("richTextTitle") String richTextTitle,
                                         @RequestParam("richTextDescription") String richTextDescription,
                                         @RequestParam("richTextContent") String richTextContent) {

        richTextService.insertRichText(
                new RichText()
                        .setRichTextTitle(richTextTitle)
                        .setRichTextDescription(richTextDescription)
                        .setRichTextContent(richTextContent));
        return ServerResponse.createBySuccessMessage("新增成功");
    }

    @ApiOperation(value = "编辑富文本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "richTextTitle", value = "标题", required = true, type = "string"),
            @ApiImplicitParam(name = "richTextDescription", value = "描述", required = true, type = "string"),
            @ApiImplicitParam(name = "richTextContent", value = "内容", required = true, type = "string")
    })
    @PostMapping("/updateRichText")
    public ServerResponse updateRichText(@RequestParam("richTextTitle") String richTextTitle,
                                         @RequestParam("richTextDescription") String richTextDescription,
                                         @RequestParam("richTextContent") String richTextContent,
                                         @RequestParam("id") Integer id) {
        richTextService.updateRichText(
                new RichText()
                        .setId(id)
                        .setRichTextTitle(richTextTitle)
                        .setRichTextDescription(richTextDescription)
                        .setRichTextContent(richTextContent));
        return ServerResponse.createBySuccessMessage("编辑成功");
    }


    @ApiOperation(value = "删除富文本信息")
    @ApiImplicitParam(name = "ids", value = "编号", required = true, type = "integer[]")
    @PostMapping("/deleteRichText")
    public ServerResponse deleteRole(@RequestParam("ids") Integer[] ids) {
        richTextService.deleteRichText(ids);
        return ServerResponse.createBySuccessMessage("删除成功");
    }


    @ApiOperation(value = "根据编号查询")
    @ApiImplicitParam(name = "id", value = "编号", required = true, type = "integer")
    @PostMapping("/findById")
    public ServerResponse findById(Integer id) {
        richTextService.findById(id);
        return ServerResponse.createBySuccessMessage("删除成功");
    }
}
