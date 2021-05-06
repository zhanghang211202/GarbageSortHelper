package top.miyamoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 语音识别记录
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
@ApiModel(value="SpeechClassify对象", description="语音识别记录")
public class SpeechClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String filepath;

    private String oneKeyword;

    private String oneResult;

    private String allKeyword;

    private String allResult;

    private String userId;

    private Date times;


}
