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
 * 幻灯片播放表
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
@ApiModel(value="SlideShow对象", description="幻灯片播放表")
public class SlideShow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "slide_id", type = IdType.AUTO)
    private Integer slide;

    @ApiModelProperty(value = "分类id")
    private Integer sortId;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

}
