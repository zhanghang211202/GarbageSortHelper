package top.miyamoto.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.miyamoto.entity.ChallengeDetail;
import top.miyamoto.entity.ChallengeResult;
import top.miyamoto.entity.IncorrectList;
import top.miyamoto.service.ChallengeResultService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 挑战赛接口
 * @author: ZAG
 * @time: 2021/2/23 0023 11:02
 */
@RestController
@RequestMapping("/challenge")
@Api(value = "挑战赛 Controller",tags = {"挑战赛接口统计"})
@Log4j2
public class ChallengeController {
    @Autowired
    ChallengeResultService challengeResultService;

    @GetMapping("/incorrectList/{userId}")
    @ApiOperation(value = "获取用户的错题")
    public R searchIncorrectList(@PathVariable(value = "userId") String userId){
        if(StringUtils.isEmpty(userId)){
            return null;
        }
        QueryWrapper<ChallengeResult> wrapper = new QueryWrapper<>();
        List<ChallengeResult> all = challengeResultService.list(wrapper.eq("user_id",userId).orderByDesc("times"));
        System.out.println(all);
        List<IncorrectList> res = new ArrayList<>();
        for(ChallengeResult result : all){
            List<ChallengeDetail> incorrectList = JSONObject.parseArray(result.getResult(), ChallengeDetail.class).stream()
                    .filter(d -> d.getWhether() != 1)
                    .collect(Collectors.toList());
            if(incorrectList.size()!=0){
                res.add(new IncorrectList(result.getTimes(),incorrectList));
            }
        }
        return R.ok(res);
    }

    @PostMapping
    @ApiOperation(value = "处理挑战赛的结果")
    public R challengeResults(@RequestBody Map<String,Object> map){
        Map<String, Object> map1 = challengeResultService.saveChallengeResults(map);
        return R.ok(map1);
    }
}
