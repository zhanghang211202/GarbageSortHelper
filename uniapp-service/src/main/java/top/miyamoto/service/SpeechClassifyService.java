package top.miyamoto.service;

import top.miyamoto.entity.QuestionBank;
import top.miyamoto.entity.SpeechClassify;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 语音识别记录 服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface SpeechClassifyService extends IService<SpeechClassify> {
    void speechHandle(String fileName, String speechResult, List<QuestionBank> list, String speechStr, QuestionBank questionBankOne,String userId);
}
