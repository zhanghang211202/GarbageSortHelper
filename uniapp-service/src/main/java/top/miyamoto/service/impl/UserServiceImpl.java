package top.miyamoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import top.miyamoto.common.ConnUtil;
import top.miyamoto.entity.User;
import top.miyamoto.mapper.UserMapper;
import top.miyamoto.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Service
@Log4j2
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Value("${wx.appid}")
    String appid;
    @Value("${wx.secret}")
    String secret;
    @Override
    public String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String str = ConnUtil.httpRequest(url,"GET",null);
        JSONObject jsonObject = JSON.parseObject(str);
        return jsonObject.get("openid").toString();
    }
}
