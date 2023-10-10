package com.sup1x.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sup1x.api.security.jwt.AuthEntryPointJwt;
import com.sup1x.api.security.jwt.AuthTokenFilter;
import com.sup1x.api.security.services.UserDetailsServiceImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {

    /*@Value("${spring.h2.console.path}")
    private String h2ConsolePath;*/

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .antMatchers(h2ConsolePath + "/**").permitAll()
//      .anyRequest().authenticated();
//
//    // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
//    http.headers().frameOptions().sameOrigin();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/password-reset/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/test/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/articles/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/comments/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/files/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/cookies/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/notifications/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/articles/tags/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/tags/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/pin/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/version/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/git/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/github/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/projects/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/v1/database/**")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).hasRole("ADMIN")
                                .anyRequest().authenticated()
                );
        http.cors(cors -> cors.configurationSource(corsWebFilter()));

        // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
        http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.applyPermitDefaultValues();

        return request -> configuration;
    }

}
