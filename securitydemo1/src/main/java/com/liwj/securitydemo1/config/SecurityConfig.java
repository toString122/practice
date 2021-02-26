package com.liwj.securitydemo1.config;

import com.liwj.securitydemo1.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()//自定义编写的html页面
                .loginPage("/login.html") //登陆页面设置
                .loginProcessingUrl("/user/login")//登陆访问路径
                .defaultSuccessUrl("/test/index").permitAll()//登录成功跳转的地址
                .and()
                .authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll()//这些路径可以直接访问 ，不需要认证
                .anyRequest().authenticated() //任何请求都必须经过身份验证
                .and().csrf().disable();//关闭csrf防护
    }
}
