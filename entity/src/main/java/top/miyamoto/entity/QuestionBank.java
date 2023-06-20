package top.miyamoto.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 题库表
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="QuestionBank对象", description="题库表")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "question_id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer questionId;

    @ApiModelProperty(value = "垃圾名称")
    @ExcelProperty(index = 0)
    private String garbageName;

    @ApiModelProperty(value = "垃圾类别")
    @ExcelProperty(index = 1)
    private Integer garbageType;

    @ApiModelProperty(value = "解析")
    @ExcelProperty(index = 2)
    private String analysis;

    @ApiModelProperty(value = "备注")
    @ExcelProperty(index = 3)
    private String remark;


}
