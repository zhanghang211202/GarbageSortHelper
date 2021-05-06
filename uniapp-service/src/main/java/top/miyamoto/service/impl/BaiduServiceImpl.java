package top.miyamoto.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.miyamoto.common.ArsMain;
import top.miyamoto.common.DemoException;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.service.BaiduService;
import top.miyamoto.service.ImageClassifyService;
import top.miyamoto.service.QuestionBankService;
import top.miyamoto.service.SpeechClassifyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 百度服务接口
 * @author: ZAG
 * @time: 2021/2/23 0023 10:53
 */
@Service
@Log4j2
public class BaiduServiceImpl implements BaiduService {
    @Value("${baidu.appid}")
    String appid;
    @Value("${baidu.apikey}")
    String apikey;
    @Value("${baidu.secretkey}")
    String secretkey;

    @Value("${upload.record.path}")
    String recordPath;

    @Value("${upload.image.path}")
    String imagePath;

    @Autowired
    ArsMain arsMain;

    @Autowired
    QuestionBankService qbService;

    @Autowired
    SpeechClassifyService speechClassifyService;

    @Autowired
    ImageClassifyService imageClassifyService;

    @Override
    public Map<String, Object> apiSpeech(String fileName,String userId){
        String speechResult = "";
        List<QuestionBank> list = new ArrayList<>();
        String speechStr = "";

        try {
            speechResult =arsMain.runJsonPostMethod(fileName);
            log.info(speechResult);
            JSONObject object = JSON.parseObject(speechResult);
            JSONArray results =object.getJSONArray("result");
            String[] split = results.getString(0).split("。");
            if(split.length==0) {
                throw new DemoException("语音识别中出了点小差");
            }
            speechStr = split[0];
            list = qbService.list(new QueryWrapper<QuestionBank>().like("garbage_name", speechStr));
            System.out.println(object.toString());
        } catch (DemoException e) {
            log.error("demoException:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("IOException:" + e.getMessage());
            e.printStackTrace();
        }
        QuestionBank questionBankOne = qbService.getOne(new QueryWrapper<QuestionBank>().eq("garbage_name", speechStr));
        log.error("speech 111");
        //异步保存 识别结果
        speechClassifyService.speechHandle(fileName, speechResult, list, speechStr, questionBankOne,userId);
        log.error("speech 222");
        log.info(speechResult);
        System.out.println("---");
        log.info("speech:——> keyword:"+ speechStr);
        log.info("speech:——> results:"+list.toString());
        log.info("speech:——> response:"+speechResult);
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", speechStr);
        map.put("results", list);
        map.put("response", speechResult);
        map.put("uni",questionBankOne);
        return map;
    }

    @Override
    public Map<String, Object> imageClassify(String filename,String userId) {
        System.out.println("imageClassify");
        AipImageClassify client = new AipImageClassify(appid, apikey, secretkey);
        HashMap<String, String> options = new HashMap<>();
        org.json.JSONObject res = client.advancedGeneral(filename, options);
        org.json.JSONArray results = res.getJSONArray("result");
        org.json.JSONObject resultVo = results.getJSONObject(0);
        log.error("score:"+resultVo.getDouble("score"));
        log.error(resultVo.getDouble("score")>0.8);
        QuestionBank questionBankOne=null;
        List<QuestionBank> questionBanks = new ArrayList<>();
        if(resultVo.getDouble("score")>0.8){
            questionBankOne = qbService.getOne(new QueryWrapper<QuestionBank>().eq("garbage_name", resultVo.getString("keyword")));
            questionBanks = qbService.list(new QueryWrapper<QuestionBank>().like("garbage_name", resultVo.getString("keyword")));
        }

        imageClassifyService.imageHandle(filename, res, resultVo, null, null,userId);

        Map<String, Object> map = new HashMap<>();
        map.put("results", questionBanks);
        map.put("keyword", resultVo.getString("keyword"));
        map.put("response", res.toString());
        map.put("uni",questionBankOne);
        return map;

    }


}
