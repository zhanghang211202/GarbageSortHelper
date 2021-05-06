import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.miyamoto.UniAppApiApplication;
import top.miyamoto.entity.User;
import top.miyamoto.service.UserService;
import java.util.HashMap;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/17 0017 9:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniAppApiApplication.class)
public class WrapperTest {
    @Autowired
    private UserService userService;
    @Test
    public void test01(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("nick_name")
                .eq("gender",1);
        userService.list(wrapper).forEach(System.out::println);
    }

    @Test
    public void test02(){
        //过滤查找
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id",1);
        map.put("nick_name","张三");
        map.put("gender",0);
        //等价于：SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user WHERE gender = ?
        //这个过滤器把k.indexOf("g")!=0的k全都过滤掉了 等于0的留下 只有 gender字段满足
        userService.list(wrapper1.allEq((k,v)->k.indexOf("g")==0,map)).forEach(System.out::println);
        //wrapper不要复用，最好新建
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        //等价于：SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user WHERE user_id = ? AND nick_name = ?
        //非等于0的留下，等于0的不留下
        userService.list(wrapper2.allEq((k,v)->k.indexOf("g")!=0,map)).forEach(System.out::println);
    }

    @Test
    public void test03(){
        //逻辑查找
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        //SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user WHERE gender > ?
        userService.list(wrapper1.gt("gender",-1)).forEach(System.out::println);

        //wrapper不要复用，最好新建 不然上面的gt与下面的bt会叠加
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        //SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user WHERE gender > ? AND gender BETWEEN ? AND ?
        userService.list(wrapper2.between("gender",2,4)).forEach(System.out::println);
    }

    @Test
    public void test04(){
        //in
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        // SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user WHERE user_id IN (select user_id from user where user_id < 3)
        userService.list(wrapper1.inSql("user_id", "select user_id from user where user_id < 3")).forEach(System.out::println);
    }

    @Test
    public void test05(){
        //排序
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        //SELECT user_id,nick_name,avatar_url,use_language,gender,country,province,city FROM user ORDER BY user_id DESC
        userService.list(wrapper1.orderByDesc("user_id")).forEach(System.out::println);
    }

}
