package web.kursach.hobby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import web.kursach.hobby.service.UserService;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CustomPermissionEvaluator();
    } 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests                             
                            /* 
                            .requestMatchers("users/register").permitAll()
                            
                            .requestMatchers(HttpMethod.GET, "/hobbies/**").permitAll() 
                            .requestMatchers(HttpMethod.GET, "/comments/**").permitAll()     
                            
                            .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/comments/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/users/**").authenticated()

                            .requestMatchers(HttpMethod.PUT, "/comments/**").hasRole("ADMIN")  
                            .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN") 
                            .requestMatchers(HttpMethod.DELETE, "/comments/**").hasRole("ADMIN") 
                            .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN") 

                            .requestMatchers(HttpMethod.PUT, "/hobbies/*").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/hobbies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/hobbies/*").hasRole("ADMIN")


                            //.anyRequest().hasRole("ADMIN") 
                            //.anyRequest().permitAll()
                            .anyRequest().authenticated()
                            */
                            .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(login ->
                    login                            
                            //.loginPage("/login")
                            .defaultSuccessUrl("/hobbies", true)
                            .permitAll()
            )
            .logout(logout ->
                    logout  
                            //.logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .permitAll()
            )
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling
                            .accessDeniedPage("/access-denied")
            )
            .csrf(csrf -> csrf.disable()); 
        
        return http.build();
    }

}
