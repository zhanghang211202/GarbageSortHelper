package top.miyamoto.mapper;

import org.apache.ibatis.annotations.Param;
import top.miyamoto.entity.QuestionBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 题库表 Mapper 接口
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {
    List<QuestionBank> qbRandOne(@Param("num") Integer num);
}
