package top.miyamoto.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.miyamoto.entity.User;
import top.miyamoto.service.UserService;

import java.util.List;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/14 0014 10:10
 */
@RestController
@RequestMapping
@Api(value="用户 Controller",tags={"用户登录接口"})
@Log4j2
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/showUsers")
    @ApiOperation(value = "查找所有用户")
    public List<User> showUsers(){
        return userService.list();
    }

    @GetMapping("/getOpenId/{code}")
    @ApiOperation(value = "获取openId")
    public String getOpenId(@PathVariable(value = "code") String code){
        if(StringUtils.isEmpty(code)){
            return null;
        }
        return userService.getOpenId(code);
    }
}
