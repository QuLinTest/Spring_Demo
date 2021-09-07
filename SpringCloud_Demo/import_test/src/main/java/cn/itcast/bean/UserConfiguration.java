package cn.itcast.bean;

import org.springframework.context.annotation.Bean;


/**
 *
 */
public class UserConfiguration {

    @Bean
    public User getUser(){
        User user = new User();

        user.setUserName("z贼贼");
        user.setAge(12);
        return user;
    }
}
