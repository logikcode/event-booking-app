//package com.bw.reference.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.encrypt.Encryptors;
//
//import javax.sql.DataSource;
//
///**
// * @author Neme Iloeje niloeje@byteworks.com.ng
// */
//@Configuration
//@EnableSocial
//public class SocialConfig implements SocialConfigurer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public FacebookConnectionFactory facebookConnectionFactory() {
//        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory("AppID", "AppSecret");
//        facebookConnectionFactory.setScope("email");
//        return facebookConnectionFactory;
//    }
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
//        cfConfig.addConnectionFactory(facebookConnectionFactory());
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
//        return new AuthenticationNameUserIdSource();
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//    }
//
//}
