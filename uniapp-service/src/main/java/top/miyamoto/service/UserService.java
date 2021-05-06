package top.miyamoto.service;

import top.miyamoto.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface UserService extends IService<User> {
    /**
     * 获取openId
     * @param code
     * @return
     */
    String getOpenId(String code);
}
