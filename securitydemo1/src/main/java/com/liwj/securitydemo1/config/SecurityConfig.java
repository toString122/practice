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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import sun.security.util.Password;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;
    //注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

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
        http.exceptionHandling().accessDeniedPage("/unauth.html");//配置403页面 没有访问权限
        http.formLogin()//自定义编写的html页面
                .loginPage("/login.html") //登陆页面设置
                .loginProcessingUrl("/user/login")//登陆访问路径
                .defaultSuccessUrl("/success.html").permitAll()//登录成功跳转的地址
                .and()
                .authorizeRequests()
                .antMatchers("/","/test/hello","/user/login","/login").permitAll()//这些路径可以直接访问 ，不需要认证
                .antMatchers("/test/index").hasAnyAuthority("admins")
                .anyRequest().authenticated() //任何请求都必须经过身份验证
                .and().rememberMe().tokenRepository(persistentTokenRepository())//自动登录
                .tokenValiditySeconds(60)//有效时长 秒
                .userDetailsService(userDetailsService)
                .and().csrf().disable();//关闭csrf防护
        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/test/hello").permitAll();//注销登录


    }
}
