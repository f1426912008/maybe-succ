package com.test.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MySecurity extends WebSecurityConfigurerAdapter {

    /**
     * 认证
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* 链式编程：
         * antMatchers(String... pattern): 要拦截的接口名，不限参数数量
         * permitAll(): 允许全部请求
         * denyAll(): 拒绝全部请求
         * hasRole(String role): 仅某一角色可访问
         * hasAnyRole(String... roles): 多个角色可访问
         */
        http.authorizeRequests()
                .antMatchers("/").denyAll()
                .antMatchers("/toLogin").permitAll()
                .antMatchers("/manyRole").hasAnyRole("vip1", "vip2")
                .antMatchers("/role1").hasRole("vip1")
                .antMatchers("/role2").hasRole("vip2")
                .antMatchers("/role3").hasRole("vip3");

        // formLogin(): 没有权限，将自动跳转至系统默认登陆页面
        http.formLogin();

        // 退出登录
        http.logout();
    }

    /**
     * 授权
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 强散列哈希加密，适用高版本Spring Security，需要对明文密码进行加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        /**
         * 配置授权方式：
         * inMemoryAuthentication()：从内存中加载授权用户
         * jdbcAuthentication()：从数据库中读取授权用户
         * userDetailsService(UserDetailsService service)：自定义授权方式，需要自定义类实现 UserDetailsService 接口
         *
         * and()：连接符
         */
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("admin").password(encoder.encode("123456")).roles("vip1")
                .and()
                .withUser("guest").password(encoder.encode("123456")).roles("vip2", "vip3")
                .and()
                .withUser("root").password(encoder.encode("123456")).roles("vip1", "vip2", "vip3");

    }
}
