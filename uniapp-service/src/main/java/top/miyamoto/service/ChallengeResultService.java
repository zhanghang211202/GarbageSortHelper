package top.miyamoto.service;

import top.miyamoto.entity.ChallengeResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 挑战结果+详情记录表 服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface ChallengeResultService extends IService<ChallengeResult> {
    Map<String ,Object> saveChallengeResults(Map<String,Object> map);
}
