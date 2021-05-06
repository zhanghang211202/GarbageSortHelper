package top.miyamoto.service;

import org.json.JSONObject;
import top.miyamoto.entity.ImageClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import top.miyamoto.entity.QuestionBank;

import java.util.List;

/**
 * <p>
 * 图像识别记录 服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface ImageClassifyService extends IService<ImageClassify> {
    void imageHandle(String filename, JSONObject res, JSONObject resultVo, List<QuestionBank> questionBanks, QuestionBank questionBankOne,String userId);
}
