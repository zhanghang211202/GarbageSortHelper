package top.miyamoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import top.miyamoto.entity.ImageClassify;
import top.miyamoto.entity.KeywordResult;
import top.miyamoto.entity.KeywordSearchNum;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.mapper.ImageClassifyMapper;
import top.miyamoto.service.ImageClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.miyamoto.service.KeywordResultService;
import top.miyamoto.service.KeywordSearchNumService;

import java.util.List;

/**
 * <p>
 * 图像识别记录 服务实现类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Service
@Log4j2
public class ImageClassifyServiceImpl extends ServiceImpl<ImageClassifyMapper, ImageClassify> implements ImageClassifyService {
    @Autowired
    KeywordResultService krService;
    @Autowired
    KeywordSearchNumService ksnService;

    @Async
    @Override
    public void imageHandle(String filename, JSONObject res, JSONObject resultVo, List<QuestionBank> questionBanks, QuestionBank questionBankOne,String userId) {
        final JSONArray jsonArray = res.getJSONArray("result");
        StringBuilder allKeyword=new StringBuilder();
        for (int i=0;i<jsonArray.length();i++) {
            allKeyword.append(jsonArray.getJSONObject(i).getString("keyword")+",");
        }
        ImageClassify imageClassify = ImageClassify.builder().oneKeyword(resultVo.getString("keyword"))
                .oneResult(JSON.toJSONString(questionBankOne))
                .allKeyword(allKeyword.toString())
                .allResult(JSON.toJSONString(questionBanks))
                .userId("张三")
                .filepath(filename).build();
        this.save(imageClassify);

        String name = resultVo.getString("keyword");
        boolean keywordResultsSave = krService.save(KeywordResult.builder().keyword(name).result(JSON.toJSONString(questionBanks)).build());
        KeywordSearchNum keywordNum = ksnService.getOne(new QueryWrapper<KeywordSearchNum>().eq("keyword", name));
        if(StringUtils.isEmpty(keywordNum)){
            keywordNum=KeywordSearchNum.builder().keyword(name).num(1).build();
        }else{
            keywordNum.setNum(keywordNum.getNum()+1);
        }
        ksnService.saveOrUpdate(keywordNum);

    }
}
