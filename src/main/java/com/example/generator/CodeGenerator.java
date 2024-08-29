package com.example.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

//演示例子，執行 main 方法控制台輸入模塊表名回車自動生成對應項目目錄中
public class CodeGenerator {

 /**
  * <p>
  * 讀取控制台內容
  * </p>
  */
 public static String scanner(String tip) {
     Scanner scanner = new Scanner(System.in);
     StringBuilder help = new StringBuilder();
     help.append("請輸入" + tip + "：");
     System.out.println(help.toString());
     if (scanner.hasNext()) {
         String ipt = scanner.next();
         if (StringUtils.isNotBlank(ipt)) {
             return ipt;
         }
     }
     throw new MybatisPlusException("請輸入正確的" + tip + "！");
 }

 public static void main(String[] args) {
     // 代碼生成器
     AutoGenerator mpg = new AutoGenerator();

     // 全局配置
     GlobalConfig gc = new GlobalConfig();
     String projectPath = System.getProperty("user.dir");
     gc.setOutputDir(projectPath + "/src/main/java");
     gc.setAuthor("lai");
     gc.setOpen(false);
     
     // xml 開起BaseResultMap
     gc.setBaseResultMap(true);
     
     // xml 開起BaseColumnList
     gc.setBaseColumnList(true);
     
     gc.setDateType(DateType.ONLY_DATE);
     // gc.setSwagger2(true); 實體屬性 Swagger2 注解
     mpg.setGlobalConfig(gc);

     // 數據源配置
     DataSourceConfig dsc = new DataSourceConfig();
     dsc.setUrl("jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei");
     // dsc.setSchemaName("public");
     dsc.setDriverName("com.mysql.cj.jdbc.Driver");
     dsc.setUsername("root");
     dsc.setPassword("password");
     mpg.setDataSource(dsc);

     // 包配置
     PackageConfig pc = new PackageConfig();
//     pc.setModuleName(scanner("模塊名"));
     pc.setParent("com.lai.seckillsystem");
     mpg.setPackageInfo(pc);

     // 自定義配置
     InjectionConfig cfg = new InjectionConfig() {
		@Override
		public void initMap() {
	    	 Map<String,Object> map = new HashMap<>();
	    	 map.put("date1","1.0.0");
	    	 this.setMap(map);
		}
     };

     // 如果模板引擎是 freemarker
     String templatePath = "/templates/mapper.xml.ftl";
     // 如果模板引擎是 velocity
     // String templatePath = "/templates/mapper.xml.vm";

     // 自定義輸出配置
     List<FileOutConfig> focList = new ArrayList<>();
     // 自定義配置會被優先輸出
     focList.add(new FileOutConfig(templatePath) {
         @Override
         public String outputFile(TableInfo tableInfo) {
             // 自定義輸出文件名 ， 如果你 Entity 設置了前後綴、此處注意 xml 的名稱會跟著發生變化！！
             return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
         }
     });
     /*
     cfg.setFileCreate(new IFileCreate() {
         @Override
         public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
             // 判斷自定義文件夾是否需要創建
             checkDir("調用默認方法創建的目錄，自定義目錄用");
             if (fileType == FileType.MAPPER) {
                 // 已經生成 mapper 文件判斷存在，不想重新生成返回 false
                 return !new File(filePath).exists();
             }
             // 允許生成模板文件
             return true;
         }
     });
     */
     cfg.setFileOutConfigList(focList);
     mpg.setCfg(cfg);

     // 配置模板
     TemplateConfig templateConfig = new TemplateConfig();

     // 配置自定義輸出模板
     //指定自定義模板路徑，注意不要帶上.ftl/.vm, 會根據使用的模板引擎自動識別
      templateConfig.setEntity("templates/entity2.java");
      templateConfig.setMapper("templates/mapper2.java");
      templateConfig.setService("templates/service2.java");
      templateConfig.setServiceImpl("templates/serviceImpl2.java");
      templateConfig.setController("templates/controller2.java");

     templateConfig.setXml(null);
     mpg.setTemplate(templateConfig);

     // 策略配置
     StrategyConfig strategy = new StrategyConfig();
     strategy.setNaming(NamingStrategy.underline_to_camel);
     strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//     strategy.setSuperEntityClass("你自己的父類實體,沒有就不用設置!");
     strategy.setEntityLombokModel(true);
     strategy.setRestControllerStyle(true);
     // 公共父類
//     strategy.setSuperControllerClass("你自己的父類控制器,沒有就不用設置!");
     // 寫於父類中的公共字段
//     strategy.setSuperEntityColumns("id");
     strategy.setInclude(scanner("表名，多個英文逗號分割").split(","));
     strategy.setControllerMappingHyphenStyle(true);
     strategy.setTablePrefix("t_");
     mpg.setStrategy(strategy);
     mpg.setTemplateEngine(new FreemarkerTemplateEngine());
     mpg.execute();
 }

}
