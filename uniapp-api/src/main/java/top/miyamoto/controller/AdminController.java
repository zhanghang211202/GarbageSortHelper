package top.miyamoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.miyamoto.entity.Admin;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.service.AdminService;
import top.miyamoto.service.QuestionBankService;

/**
 * @author Zhanghang
 * @date Created 2021/04/30 14:37
 * @description 管理员Controller
 */
@RestController
@RequestMapping("/admin")
@Api(value = "管理员 Controller",tags = {"管理员接口"})
@Log4j2
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    QuestionBankService questionBankService;

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    public R login(@RequestBody Admin admin){
        System.out.println("admin login");
        boolean res = adminService.adminLogin(admin);
        if(res){
            return R.ok("success");
        }
        return R.ok("error");
    }

    @PostMapping("/addQuestion")
    @ApiOperation(value = "新增问题")
    public R addQuestion(@RequestBody QuestionBank questionBank){
        boolean res = questionBankService.save(questionBank);
        if(res){
            return R.ok("success");
        }
        return R.ok("error");
    }

    @PostMapping("/updateQuestion")
    @ApiOperation(value = "更新问题")
    public R updateQuestion(@RequestBody QuestionBank questionBank){
        boolean res = questionBankService.update(questionBank,new QueryWrapper<QuestionBank>().eq("question_id",questionBank.getQuestionId()));
        if(res){
            return R.ok("success");
        }
        return R.ok("error");
    }


}

