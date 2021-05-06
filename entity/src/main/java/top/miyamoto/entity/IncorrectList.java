package top.miyamoto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/5/6 0006 21:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncorrectList {
    private Date date;
    private List<ChallengeDetail> list;
}
