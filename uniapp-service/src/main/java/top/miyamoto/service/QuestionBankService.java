package top.miyamoto.service;

import top.miyamoto.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface QuestionBankService extends IService<QuestionBank> {
    Map<String,Object> searchQuestionByNamePre(String name);

    Map<String,Object> uploadExcel(String filePath);
}
