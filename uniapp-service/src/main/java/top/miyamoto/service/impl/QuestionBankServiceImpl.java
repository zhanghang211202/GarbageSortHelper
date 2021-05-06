package top.miyamoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import top.miyamoto.entity.KeywordResult;
import top.miyamoto.entity.KeywordSearchNum;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.mapper.QuestionBankMapper;
import top.miyamoto.service.KeywordResultService;
import top.miyamoto.service.KeywordSearchNumService;
import top.miyamoto.service.QuestionBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {
    @Autowired
    QuestionBankService questionBankService;
    @Autowired
    KeywordResultService keywordResultService;
    @Autowired
    KeywordSearchNumService keywordSearchNumService;
    @Autowired
    QuestionBankMapper questionBankMapper;

    @Override
    public Map<String, Object> searchQuestionByNamePre(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        QuestionBank questionBank = questionBankService.getOne(new QueryWrapper<QuestionBank>().eq("garbage_name", name));
        List<QuestionBank> list = questionBankService.list(new QueryWrapper<QuestionBank>().like("garbage_name", name));
        //使用builder需要打上@Builder注解
        //存储搜索结果
        boolean save = keywordResultService.save(
                KeywordResult.builder()
                        .keyword(name)
                        .result(JSON.toJSONString(list))
                        .build());
        KeywordSearchNum keywordSearchNum = keywordSearchNumService.getOne(new QueryWrapper<KeywordSearchNum>().eq("keyword", name));
        if (StringUtils.isEmpty(keywordSearchNum)) {
            keywordSearchNum = KeywordSearchNum.builder()
                    .keyword(name)
                    .num(1)
                    .build();
        } else {
            keywordSearchNum.setNum(keywordSearchNum.getNum() + 1);
        }
        keywordSearchNumService.saveOrUpdate(keywordSearchNum);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uni", questionBank);
        map.put("results", list);
        return map;
        // TODO 开启事务
    }

    @Override
    public Map<String, Object> uploadExcel(String filePath) {
        return null;
    }
}
