package top.miyamoto.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.mapper.QuestionBankMapper;
import top.miyamoto.service.QuestionBankService;

import java.util.List;

/**
 * @description: 题库访问接口
 * @author: ZAG
 * @time: 2021/2/27 0027 14:01
 */
@RestController
@RequestMapping("/qb")
@Api(value = "题库 Controller",tags = {"题库访问接口"})
public class QuestionBankController {
    @Autowired
    QuestionBankService qbService;

    @Autowired
    QuestionBankMapper questionBankMapper;
    @GetMapping("/randOne")
    @ApiOperation(value = "从题库中选取一定数量的随机垃圾")
    public R qbRandOne(@RequestParam(required = false,name = "num") Integer num){
        if(StringUtils.isEmpty(num)) {
            num=1;
        }
        List<QuestionBank> qbRandOne = questionBankMapper.qbRandOne(num);
        return R.ok(qbRandOne);
    }
}
