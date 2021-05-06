package top.miyamoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.miyamoto.entity.KeywordSearchNum;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.service.KeywordSearchNumService;
import top.miyamoto.service.QuestionBankService;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/18 0018 9:44
 */
@RestController
@RequestMapping("/search")
@Api(value = "搜索 controller", tags={"搜索接口"})
public class SearchController {
    @Autowired
    QuestionBankService questionBankService;

    @Autowired
    KeywordSearchNumService searchNumService;


    @GetMapping("/name/{name}")
    @ApiOperation(value = "根据名称模糊查询垃圾")
    public R searchByName(@PathVariable(value = "name") String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        QueryWrapper<QuestionBank> wrapper = new QueryWrapper<>();
        List<QuestionBank> list = questionBankService.list(wrapper.like("garbage_name", name));
        return R.ok(list);
    }

    @GetMapping("/uniname/{name}")
    @ApiOperation(value = "根据名称精确查询垃圾分类")
    public R searchByNamePre(@PathVariable(value = "name") String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        Map<String, Object> map = questionBankService.searchQuestionByNamePre(name);
        return R.ok(map);
    }

    @GetMapping("/top10")
    @ApiOperation(value = "查询 最热门的10个垃圾分类")
    public R topTen(){
        List<KeywordSearchNum> list = searchNumService.list(new QueryWrapper<KeywordSearchNum>().orderByDesc("num").last("limit 10"));
        return R.ok(list);
    }

    @GetMapping("/type/{type}")
    @ApiOperation(value = "根据类型来进行查询")
    public R searchQuestionByType(@PathVariable("type") Integer type){
        if(StringUtils.isEmpty(type)) {
            return null;
        }
        List<QuestionBank> list = questionBankService.list(new QueryWrapper<QuestionBank>().eq("garbage_type", type));
        return R.ok(list);
    }
}
