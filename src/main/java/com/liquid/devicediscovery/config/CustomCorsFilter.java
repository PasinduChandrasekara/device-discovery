package com.liquid.devicediscovery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration("corsFilter")
public class CustomCorsFilter extends CorsFilter {
    public CustomCorsFilter() {
        super(corsConfigurationSource());
        setCorsProcessor(new CustomCorsProcessor());
    }

    static CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList(
                HttpMethod.OPTIONS.name(), HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name()));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    static class CustomCorsProcessor extends DefaultCorsProcessor {

        @Override
        protected String checkOrigin(CorsConfiguration config, @Nullable String requestOrigin) {
            return StringUtils.hasText(requestOrigin) ? requestOrigin : "localhost";
        }
    }
}
