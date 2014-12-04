package com.springmvcpoc.controllers;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class IndexController {
               @RequestMapping("/")
               public String index(HttpServletRequest request){
                              return "index.jsp";
               }
}