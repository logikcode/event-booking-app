///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bw.reference.interceptors;
//
//
//import com.bw.commons.apiclient.ApiCallException;
//import com.bw.commons.authclient.BwAuthApiClient;
//import com.bw.commons.authclient.dto.ApiResourcePortalUser;
//import com.bw.commons.starter.TimeUtil;
//import com.bw.reference.constant.ApplicationConstants;
//import com.bw.reference.constant.TimeFormatConstants;
//import com.bw.reference.entity.PortalAccount;
//import com.bw.reference.exception.ErrorResponse;
//import com.bw.reference.principal.RequestPrincipal;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author Neme Iloeje <niloeje@byteworks.com.ng>
// */
//public class RequestPrincipalHandlerInterceptor extends HandlerInterceptorAdapter {
//
//    final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final ApplicationContext applicationContext;
//
//    private BwAuthApiClient bwAuthApiClient;
//
//    private static final int SIXTY_SECONDS = 60;
//
//    public RequestPrincipalHandlerInterceptor(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    public static FactoryBean<RequestPrincipal> requestPrincipal() {
//        return new FactoryBean<RequestPrincipal>() {
//
//            @Override
//            public RequestPrincipal getObject() {
//                if (RequestContextHolder.getRequestAttributes() == null) {
//                    return null;
//                }
//                return (RequestPrincipal) RequestContextHolder.currentRequestAttributes().getAttribute(RequestPrincipal.class.getName(),
//                        RequestAttributes.SCOPE_REQUEST);
//            }
//
//            @Override
//            public Class<?> getObjectType() {
//                return RequestPrincipal.class;
//            }
//
//            @Override
//            public boolean isSingleton() {
//                return false;
//            }
//        };
//    }
//
//    public static FactoryBean<ApiResourcePortalUser> apiResourcePortalUser() {
//        return new FactoryBean<ApiResourcePortalUser>() {
//
//            @Override
//            public ApiResourcePortalUser getObject() {
//                return (ApiResourcePortalUser) RequestContextHolder.currentRequestAttributes().getAttribute(ApiResourcePortalUser.class.getName(),
//                        RequestAttributes.SCOPE_REQUEST);
//            }
//
//            @Override
//            public Class<?> getObjectType() {
//                return ApiResourcePortalUser.class;
//            }
//
//            @Override
//            public boolean isSingleton() {
//                return false;
//            }
//        };
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        if (bwAuthApiClient == null) {
//            bwAuthApiClient = applicationContext.getBean(BwAuthApiClient.class);
//        }
//        try {
//            String authHeader = StringUtils.defaultString(request.getHeader(HttpHeaders.AUTHORIZATION), "");
//            if (StringUtils.isNotBlank(authHeader)) {
//                ApiResourcePortalUser user = bwAuthApiClient.getUserWithAuthorizationHeader(request.getHeader(HttpHeaders.AUTHORIZATION));
//                RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
//
//                String ipAddress = request.getRemoteAddr();
//                if (request.getRemoteAddr().equals(ApplicationConstants.LOCALHOST_IP_V4) || request.getRemoteAddr().equals(ApplicationConstants.LOCALHOST_IP_V6)) {
//                    ipAddress = StringUtils.defaultIfBlank(request.getHeader("X-FORWARDED-FOR"),
//                            request.getRemoteAddr());
//                }
//
//                RequestPrincipalImpl requestPrincipal = new RequestPrincipalImpl(user.getUserId(), ipAddress) {
//
//                    @Override
//                    public String getUserName() {
//                        return user.getUsername();
//                    }
//
//                    @Override
//                    public void enforceMembership(PortalAccount portalAccount) {
//                        if (!this.getAccountPermissions()
//                                .stream()
//                                .filter(accountMembershipPojo -> accountMembershipPojo.getAccountId().equals(portalAccount.getId()))
//                                .findFirst()
//                                .isPresent()) {
//                            throw new ErrorResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString(), null);
//                        }
//                    }
//                };
//                applicationContext.getAutowireCapableBeanFactory().autowireBean(requestPrincipal);
//                currentRequestAttributes.setAttribute(ApiResourcePortalUser.class.getName(),
//                        user,
//                        RequestAttributes.SCOPE_REQUEST);
//                currentRequestAttributes.setAttribute(RequestPrincipal.class.getName(),
//                        requestPrincipal,
//                        RequestAttributes.SCOPE_REQUEST);
//
//
//            }
//
//            try {
//                Cookie cookie = new Cookie(RequestPrincipal.AUTH_TOKEN_NAME, authHeader.replace("Bearer ", ""));
//                cookie.setMaxAge(SIXTY_SECONDS * 30);
//                cookie.setPath("/");
//                cookie.setHttpOnly(true);
//                cookie.setSecure(true);
//                response.addCookie(cookie);
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            }
//        } catch (ApiCallException e) {
//            if (e.getCode() != 401) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//        return true;
//    }
//}
