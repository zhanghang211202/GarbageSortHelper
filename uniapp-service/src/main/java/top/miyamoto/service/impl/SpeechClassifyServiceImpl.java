package top.miyamoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import top.miyamoto.entity.KeywordResult;
import top.miyamoto.entity.KeywordSearchNum;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.entity.SpeechClassify;
import top.miyamoto.mapper.SpeechClassifyMapper;
import top.miyamoto.service.KeywordResultService;
import top.miyamoto.service.KeywordSearchNumService;
import top.miyamoto.service.SpeechClassifyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 语音识别记录 服务实现类
 * @author ZAG
 * @since 2021-02-17
 */
@Service
@Log4j2
public class SpeechClassifyServiceImpl extends ServiceImpl<SpeechClassifyMapper, SpeechClassify> implements SpeechClassifyService {

    @Autowired
    KeywordResultService krService;
    @Autowired
    KeywordSearchNumService ksnService;

    @Async
    @Override
    public void speechHandle(String fileName, String speechResult, List<QuestionBank> list, String speechStr, QuestionBank questionBankOne,String userId) {
        log.error("speech 333");
        SpeechClassify speechClassify = SpeechClassify.builder().oneKeyword(speechStr)
                .oneResult(JSON.toJSONString(questionBankOne))
                .allKeyword(speechResult)
                .allResult(JSON.toJSONString(list))
                .userId(userId)
                .filepath(fileName).build();
        //存储speechClassify
        this.save(speechClassify);

        String name = speechStr;
        //在关键字结果表中储存搜索结果
        boolean keywordResultsSave = krService.save(KeywordResult.builder().keyword(name).result(JSON.toJSONString(list)).build());
        //更新关键字对应的搜索次数
        KeywordSearchNum keywordNum = ksnService.getOne(new QueryWrapper<KeywordSearchNum>().eq("keyword", name));
        if(StringUtils.isEmpty(keywordNum)){
            keywordNum=KeywordSearchNum.builder().keyword(name).num(1).build();
        }else{
            keywordNum.setNum(keywordNum.getNum()+1);
        }
        ksnService.saveOrUpdate(keywordNum);
    }
}
