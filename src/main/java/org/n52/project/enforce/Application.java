package org.n52.project.enforce;

//import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>
 * Application class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@SpringBootApplication
@EntityScan(basePackages = {"org.n52.project.enforce.**"})
@EnableJpaRepositories(basePackages = {"org.n52.project.enforce.**"})
@ComponentScan(basePackages = {"org.n52.project.enforce.**"})
public class Application {

    /**
     * <p>
     * main.
     * </p>
     *
     * @param args
     *            an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

//    /**
//     * <p>
//     * jtsModule.
//     * </p>
//     *
//     * @return a {@link org.n52.jackson.datatype.jts.JtsModule} object
//     */
//    @Bean
//    public JtsModule jtsModule() {
//        return new JtsModule();
//    }

}