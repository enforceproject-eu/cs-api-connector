package org.n52.project.enforce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * WebConfig class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
//@Configuration
//public class CsApiConnectorWebConfig implements WebMvcConfigurer {
////    
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/swagger-ui/**")
////                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
////                .resourceChain(false);
////    }
////    
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/swagger-ui/").setViewName("forward:" + "/swagger-ui/index.html");
//    }
//    
////    public void configureApiVersioning(ApiVersionConfigurer configurer) {
////        configurer.addSupportedVersions("1.0", "2.0")
////          .setDefaultVersion("1.0")
////          .useRequestHeader("X-API-Version");
////    }
////    
//}
