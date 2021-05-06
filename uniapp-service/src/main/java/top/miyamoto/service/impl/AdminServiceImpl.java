package top.miyamoto.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import top.miyamoto.entity.Admin;
import top.miyamoto.mapper.AdminMapper;
import top.miyamoto.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZAG
 * @since 2021-03-03
 */
@Service
@Log4j2
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Value("${admin.admin-name}")
    String adminName;
    @Value("${admin.admin-password}")
    String adminPassword;
    @Autowired
    AdminMapper adminMapper;
    @Override
    public boolean adminLogin(Admin admin) {
        if(adminName.equals(admin.getAdminName()) &&
                adminPassword.equals(admin.getAdminPassword())){
            return true;
        }
        return false;
    }
}
