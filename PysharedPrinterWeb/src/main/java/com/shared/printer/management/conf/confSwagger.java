package com.shared.printer.management.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  // 标记配置类
@EnableSwagger2 // 开启在线接口文档 方便测试
public class confSwagger {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) // 创建API基本信息
                .apiInfo(apiInfo()) // 创建API基本信息
                .select() // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com"))  //对com包下的api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口列表") // 标题
                .description("基于Springboot+微信小程序的共享打印系统 后台接口API") // 描述
                .version("1.6") // 版本
                .build(); // 创建
    }
    //文档地址：http://localhost:8090/swagger-ui.html
}
