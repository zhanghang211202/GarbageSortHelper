import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * @description: 代码自动生成器
 * @author: ZAG
 * @time: 2021/2/15 0015 9:56
 */
public class AutoCode {
    public static void main(String[] args) {
        //需要构建代码生成器对象
        AutoGenerator autoGenerator = new AutoGenerator();

        //配置策略
        //1.全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //获取项目路径
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath)
                .setAuthor("ZAG")
                .setOpen(false)//是否打开资源管理器
                .setFileOverride(true)//是否覆盖
                .setServiceName("%sService")//去Service的I前缀
                .setIdType(IdType.ID_WORKER)
                .setDateType(DateType.ONLY_DATE)
                .setSwagger2(true);//使用swagger
        autoGenerator.setGlobalConfig(globalConfig);

        //2.设置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/garbagesort?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("123456")
                .setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dataSourceConfig);

        //3. 包的配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("top.miyamoto");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        autoGenerator.setPackageInfo(packageConfig);

        //4. 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //控制 不生成 controller  空字符串就行
        templateConfig.setController("");
        autoGenerator.setTemplate(templateConfig);

        //5. 配置策略 里面存放你要生成的类的名字
        String[] tables = new String[]{
                "challenge_result"
        };
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tables);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        //设置逻辑删除字段
//        strategy.setLogicDeleteFieldName("deleted");
//        //创建策略与修改策略
//        TableFill gmt_create = new TableFill("gmt_create", FieldFill.INSERT);
//        TableFill gmt_modified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
//        ArrayList<TableFill> tableFills = new ArrayList<TableFill>;
//        tableFills.add(gmt_modified);
//        tableFills.add(gmt_create);
//        strategy.setTableFillList(tableFills);
//        //乐观锁
//        strategy.setVersionFieldName("version");

        autoGenerator.setStrategy(strategy);
        //执行
        autoGenerator.execute();
    }
}
