package top.miyamoto.service;

import com.baomidou.mybatisplus.extension.api.R;
import top.miyamoto.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-03-03
 */
public interface AdminService {
    /**
     * 管理员登录
     * @param admin
     * @return 是否成功
     */
    boolean adminLogin(Admin admin);
}
