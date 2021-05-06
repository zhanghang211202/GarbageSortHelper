package top.miyamoto.entity;

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
@Accessors(chain = true)
@ApiModel(value="QuestionBank对象", description="题库表")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    private Integer garbageType;

    @ApiModelProperty(value = "垃圾名称")
    private String garbageName;

    @ApiModelProperty(value = "解析")
    private String analysis;

    @ApiModelProperty(value = "备注")
    private String remark;


}
