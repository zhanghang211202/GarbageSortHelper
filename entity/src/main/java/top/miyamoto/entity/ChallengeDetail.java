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
 * 挑战明细记录
 * </p>
 *
 * @author ZAG
 * @since 2021-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value="ChallengeDetail对象", description="挑战明细记录")
public class ChallengeDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "是否正确；1:正确；0:错误")
    private Integer whether;

    @ApiModelProperty(value = "问题id ")
    private Integer questionId;

    private String garbageName;

    private Integer garbageType;

    private Integer selectedType;


}
