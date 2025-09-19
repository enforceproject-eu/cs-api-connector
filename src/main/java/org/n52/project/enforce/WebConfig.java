package org.n52.project.enforce;

import java.util.List;

import org.n52.project.enforce.api.ExcelFileMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * WebConfig class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /** {@inheritDoc} */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(new ExcelFileMessageConverter());
//        super.configureMessageConverters(messageConverters);
    }
    
}
