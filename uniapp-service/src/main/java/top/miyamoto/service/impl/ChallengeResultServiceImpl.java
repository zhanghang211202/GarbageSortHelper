package top.miyamoto.service.impl;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import top.miyamoto.entity.ChallengeDetail;
import top.miyamoto.entity.ChallengeResult;
import top.miyamoto.mapper.ChallengeResultMapper;
import top.miyamoto.service.ChallengeDetailService;
import top.miyamoto.service.ChallengeResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 挑战结果+详情记录表 服务实现类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Service
public class ChallengeResultServiceImpl extends ServiceImpl<ChallengeResultMapper, ChallengeResult> implements ChallengeResultService {
    @Autowired
    ChallengeDetailService detailService;

    @Override
    public Map<String, Object> saveChallengeResults(Map<String, Object> map) {
        System.out.println(map);
        Integer score = (Integer) map.get("score");
        String userId = (String) map.get("userId");
        List<LinkedHashMap> list = (List<LinkedHashMap>) map.get("list");
        List<ChallengeDetail> detailArrayList = new ArrayList<>();
        Integer whether;
        Integer garbageType;
        Integer selectedType;
        //从list中获取ChallengeDetail 并加入detailArrayList中
        for (LinkedHashMap linkedHashMap : list) {
            garbageType = (Integer) linkedHashMap.get("garbageType");
            selectedType = (Integer) linkedHashMap.get("selectedType");
            whether = (garbageType.equals(selectedType)) ? 0:1;
            ChallengeDetail detail = ChallengeDetail.builder()
                    .whether(whether)
                    .questionId((Integer) linkedHashMap.get("questionId"))
                    .garbageName((String) linkedHashMap.get("garbageName"))
                    .garbageType(garbageType)
                    .selectedType(selectedType).build();
            detailArrayList.add(detail);
        }
        //记录challengeResults
        boolean save = this.save(ChallengeResult.builder().score(score).userId(userId).result(JSON.toJSONString(detailArrayList)).build());
        //记录challengeDetail
        boolean saveBatch = detailService.saveBatch(detailArrayList);
        map.clear();
        map.put("handle", "ok");
        return map;
    }
}
