    package com.tek_up.elearning.config;

    import com.tek_up.elearning.config.jwt.AuthEntryPointJwt;
    import com.tek_up.elearning.config.jwt.AuthTokenFilter;
    import com.tek_up.elearning.services.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig implements WebMvcConfigurer {

        @Autowired
        public UserService userDetailsService;
        @Autowired
        private AuthTokenFilter authTokenFilter;
        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
            return authConfig.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(request -> {
                        // Public access first
                        request.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/category/user/**").permitAll()
                                .requestMatchers("api/users/user/**").permitAll()
                                .requestMatchers("api/courses/user/**").permitAll();

                        // Role-based access control
                        request.requestMatchers("/api/category/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/category/teacher/**").hasAuthority("TEACHER")
                                .requestMatchers("/api/category/student/**").hasAuthority("STUDENT")
                                .requestMatchers("/api/cours/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/cours/teacher/**").hasAuthority("TEACHER")
                                .requestMatchers("/api/cours/student/**").hasAuthority("STUDENT")
                                .requestMatchers("/api/user/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/user/teacher/**").hasAuthority("TEACHER")
                                .requestMatchers("/api/user/student/**").hasAuthority("STUDENT");
                    })
                    .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .formLogin(Customizer.withDefaults())
                    .build();
        }



        // Global CORS configuration
        @Override
        public void addCorsMappings(CorsRegistry corsRegistry) {
            corsRegistry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowedOrigins("http://localhost:3000")
                    .exposedHeaders("Authorization")
                    .allowCredentials(true);
        }
    }