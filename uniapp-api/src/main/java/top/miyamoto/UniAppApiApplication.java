package top.miyamoto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/14 0014 9:40
 */
@SpringBootApplication
@MapperScan("top.miyamoto.mapper")
@EnableAsync
public class UniAppApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniAppApiApplication.class, args);
    }
}
