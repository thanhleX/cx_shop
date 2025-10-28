package com.chronosx.cx_shop.configurations;

import static org.springframework.http.HttpMethod.*;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.chronosx.cx_shop.filters.JwtTokenFilter;
import com.chronosx.cx_shop.models.Role;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableWebMvc
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {

    @Value("${api.prefix}")
    @NonFinal
    String apiPrefix;

    JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix))
                            .permitAll()
                            // role
                            .requestMatchers(GET, String.format("%s/roles**", apiPrefix))
                            .permitAll()

                            // category get
                            .requestMatchers(GET, String.format("%s/categories**", apiPrefix))
                            .hasAnyRole(Role.USER, Role.ADMIN)

                            // category post
                            .requestMatchers(POST, String.format("%s/categories/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // category put
                            .requestMatchers(PUT, String.format("%s/categories/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // category delete
                            .requestMatchers(DELETE, String.format("%s/categories/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // product get
                            .requestMatchers(GET, String.format("%s/products**", apiPrefix))
                            .hasAnyRole(Role.USER, Role.ADMIN)

                            // product images get
                            .requestMatchers(GET, String.format("%s/products/images/*", apiPrefix))
                            .hasAnyRole(Role.USER, Role.ADMIN)

                            // product post
                            .requestMatchers(POST, String.format("%s/products/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // product put
                            .requestMatchers(PUT, String.format("%s/products/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // product delete
                            .requestMatchers(DELETE, String.format("%s/products/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // order get
                            .requestMatchers(GET, String.format("%s/orders/**", apiPrefix))
                            .hasAnyRole(Role.USER, Role.ADMIN)

                            // order post
                            .requestMatchers(POST, String.format("%s/orders/**", apiPrefix))
                            .hasRole(Role.USER)

                            // order put
                            .requestMatchers(PUT, String.format("%s/orders/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // order delete
                            .requestMatchers(DELETE, String.format("%s/orders/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // order_details
                            .requestMatchers(GET, String.format("%s/order_details/**", apiPrefix))
                            .hasAnyRole(Role.USER, Role.ADMIN)

                            // order_details post
                            .requestMatchers(POST, String.format("%s/order_details/**", apiPrefix))
                            .hasRole(Role.USER)

                            // order_details put
                            .requestMatchers(PUT, String.format("%s/order_details/**", apiPrefix))
                            .hasRole(Role.ADMIN)

                            // order_details delete
                            .requestMatchers(DELETE, String.format("%s/order_details/**", apiPrefix))
                            .hasRole(Role.ADMIN)
                            .anyRequest()
                            .authenticated();
                });

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });

        return http.build();
    }
}
