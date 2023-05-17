package com.example.Editique.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestApiRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:C://folder1").to("file:C//folder2");

    }
}
