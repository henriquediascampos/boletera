package com.henriquediascampos.boletera;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoletoController {

    @Value("${server.servlet.context-path}")
    private String contextpath;

    @GetMapping("/")
    public void redirectToHello(HttpServletResponse response) throws IOException {
        response.sendRedirect(contextpath+"/hello");
    }
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "hello";
    }

}
