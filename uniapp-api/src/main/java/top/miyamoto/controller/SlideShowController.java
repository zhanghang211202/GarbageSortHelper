package top.miyamoto.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.miyamoto.entity.SlideShow;
import top.miyamoto.service.SlideShowService;

import java.util.List;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/18 0018 8:52
 */
@RestController
@RequestMapping("/slideShow")
@Api(value = "滑动窗口 Controller",tags = {"滑动窗口 图片访问接口"})
public class SlideShowController {
    @Autowired
    SlideShowService slideShowService;

    @GetMapping
    @ApiOperation(value = " 获取 滑动窗口的图像加载")
    public R slideShow(){
        List<SlideShow> list = slideShowService.list();
        return R.ok(list);
    }
}
