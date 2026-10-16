package org.n52.project.enforce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("${enforce.user}")
    String user;
    @Value("${enforce.password}")
    String password;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
            // @formatter:off
//            http
//                    .authorizeHttpRequests((requests) -> requests
//                            .requestMatchers("/", "/home", "/swagger-ui/**").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .formLogin((form) -> form
//                            .loginPage("/login")
//                            .permitAll()
//                    )
//                    .logout(LogoutConfigurer::permitAll);
            // @formatter:on

        http
        .authorizeHttpRequests((authorize) -> authorize
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults());
//        .formLogin(Customizer.withDefaults());
        
            http.csrf(csrf -> csrf.disable());
            
            return http.build();
        
//            http.csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> auth.requestMatchers("/v3/api-docs/**", 
//                 "/swagger-ui/**", "/swagger-ui.html")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//            .httpBasic(Customizer.withDefaults());
//    return http.build(); 
        
//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui/**")
//                  .permitAll()
//                  .requestMatchers("/v3/api-docs*/**")
//                  .permitAll());
//
//        http.csrf(csrf -> csrf.disable());
//        
//              return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) {
            UserDetails userDetails = User.withUsername(user).password(encoder.encode(password)).roles("USER").build();
            return new InMemoryUserDetailsManager(userDetails);
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//          .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
//    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
////                .httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
//        
//        http
//        .authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/").permitAll()
//                .anyRequest().authenticated()
//        )
//        .formLogin((form) -> form
//                .loginPage("/login")
//                .permitAll()
//        )
//        .logout(LogoutConfigurer::permitAll);
//        
//        http.csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails =
//                User.withDefaultPasswordEncoder().username(user).password(password).roles("USER").build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }

}
