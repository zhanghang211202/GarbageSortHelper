package top.miyamoto.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.miyamoto.entity.QuestionBank;
import top.miyamoto.mapper.QuestionBankMapper;
import top.miyamoto.service.QuestionBankService;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/14 0014 9:10
 */
@Log4j2
@Component
public class ExcelListener extends AnalysisEventListener<QuestionBank> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(top.miyamoto.entity.QuestionBank.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    List<QuestionBank> list = new ArrayList<QuestionBank>();

    private QuestionBankService questionBankService;

    public ExcelListener(QuestionBankService questionBankService){
        LOGGER.info("监听注入");
        this.questionBankService = questionBankService;
    }

    @Override
    public void invoke(QuestionBank questionBank, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(questionBank));
        list.add(questionBank);

        if(list.size() >= BATCH_COUNT){
            saveData();
            LOGGER.info("所有数据解析完成！");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData(){
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        //存储或更新
        questionBankService.saveOrUpdateBatch(list);
        LOGGER.info("存储数据库成功！");
    }
}
