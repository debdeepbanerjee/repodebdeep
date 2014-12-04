package com.springmvcpoc.config;
 
 
import javax.servlet.ServletContext;
 
 
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
 
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.springmvcpoc"})
public class WebConfig extends WebMvcConfigurerAdapter {
              
               @Autowired
    ServletContext servletContext;
 
               @Bean
               public ViewResolver viewResolver() {
                              InternalResourceViewResolver resolver = new InternalResourceViewResolver();
                              resolver.setViewClass(JstlView.class);
                              resolver.setPrefix("/WEB-INF/views/");
                              resolver.setOrder(1);
                              return resolver;
               }
 
               @Bean
               public ViewResolver fallThroughForRedirects() {
                              InternalResourceViewResolver resolver = new InternalResourceViewResolver();
                              resolver.setViewClass(JstlView.class);
                              resolver.setOrder(2);
                              return resolver;
               }
              
              
 
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                useJaf(false).
                mediaType("txt",MediaType.TEXT_PLAIN);
    }
 
 
    @Override
               public void addResourceHandlers(ResourceHandlerRegistry registry) {
                              registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
               }
 
               @Bean
               public CommonsMultipartResolver multipartResolver() {
                              return new CommonsMultipartResolver();
               }
 
               @Bean
               public LocaleResolver localeResolver() {
                              return new SessionLocaleResolver();
               }
              
               @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> httpMessageConverters) {
        httpMessageConverters.add(jacksonHttpMessageConverter2());
        httpMessageConverters.add(new StringHttpMessageConverter());
    }
               @Bean
               public ObjectMapper objectMapper2(){
               ObjectMapper newObjectMapper = new ObjectMapper();
    newObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return newObjectMapper;
               }
               @Bean
               public MappingJackson2HttpMessageConverter jacksonHttpMessageConverter2(){
                              MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter=new MappingJackson2HttpMessageConverter();
                              mappingJacksonHttpMessageConverter.setObjectMapper(objectMapper2());
                              List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
                              supportedMediaTypes.add(MediaType.APPLICATION_JSON);
                              supportedMediaTypes.add(MediaType.TEXT_PLAIN);
                              mappingJacksonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
                              return mappingJacksonHttpMessageConverter;
               }
              
               @Bean
               @Lazy
               public    MemcachedClient memcachedClient() throws IOException{
                              MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"),new int[]{1});
                              builder.setCommandFactory(new BinaryCommandFactory());//use binary protocol
                              SerializingTranscoder serializingTranscoder= new SerializingTranscoder();
                              builder.setTranscoder(serializingTranscoder);
                              MemcachedClient memcachedClient=builder.build();
                              return memcachedClient;
               }
 
}