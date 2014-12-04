package com.springmvcpoc.controllers;
 
import java.util.Date;
import java.util.concurrent.TimeoutException;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
import com.springmvcpoc.models.POCResponse;
 
 
 
@Controller
@RequestMapping("/springmvcpocgreetings")
public class PocController {
              
               static final private Logger LOGGER = LoggerFactory
                                             .getLogger(PocController.class);
              
               @Autowired
               public MemcachedClient memcachedclient;
              
               @RequestMapping(value = { "/v1/greetings" }, method = RequestMethod.POST)
               @ResponseBody
               public Object sayHello(
                                             @RequestParam(value = "nameTxt", required = true) final String name,
                                             HttpServletRequest request, final HttpServletResponse response) throws TimeoutException, InterruptedException, MemcachedException{
                              POCResponse pOCResponse=null;
                              LOGGER.info("info log name="+name);
                              LOGGER.debug("debug log name="+name);
                              LOGGER.error("error log name="+name);
                              LOGGER.warn("warn log name="+name);
                              LOGGER.trace("trace log name="+name);
                              pOCResponse=(POCResponse)memcachedclient.get(name,500);
                              if(pOCResponse==null){
                              pOCResponse=new POCResponse();
                              pOCResponse.setName(name);
                              pOCResponse.setGreetings("Hi "+ name +",how are you today. Today is:"+new Date());
                              memcachedclient.set(name,3600,pOCResponse);
                              }
                              return pOCResponse;
               }
}