package org.n52.project.enforce;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * Application class.
 * </p>
 *
 * @author Benjamin Pross (b.pross@52north.org)
 * @since 1.0.0
 */
@SpringBootApplication
@EntityScan(basePackages = {
      "org.n52.project.enforce.cs4.playas.db.model", "org.n52.project.enforce.cs4.playas.db.repository",
      "org.n52.project.enforce.db.model", "org.n52.project.enforce.db.repository",
      "org.n52.project.enforce.cs3.db.model", "org.n52.project.enforce.cs3.db.repository"})
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

    /**
     * <p>
     * jtsModule.
     * </p>
     *
     * @return a {@link org.n52.jackson.datatype.jts.JtsModule} object
     */
    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

}