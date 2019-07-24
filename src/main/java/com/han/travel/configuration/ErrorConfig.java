package com.han.travel.configuration;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorConfig implements ErrorPageRegistrar
{
    @Override
    public void registerErrorPages(ErrorPageRegistry registry)
    {
        ErrorPage[] errorPages = new ErrorPage[2];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/error");
        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error");

        registry.addErrorPages(errorPages);
    }

}
