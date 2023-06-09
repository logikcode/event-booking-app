package com.bw.reference.resolvers;

import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.principal.RequestPrincipal;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.inject.Provider;
import java.util.Optional;

/**
 * @author Neme Iloeje
 * @author Email: niloeje@byteworks.com.ng
 * @since August, 2020
 **/
public class WorkspaceUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private Provider<RequestPrincipal> requestPrincipalProvider;
    @Autowired
    private Logger logger;

    public WorkspaceUserArgumentResolver(ApplicationContext applicationContext) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.isOptional()) {
            return parameter.getNestedParameterType().equals(WorkspaceUser.class);
        }
        return parameter.getParameterType().equals(WorkspaceUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestPrincipal requestPrincipal = requestPrincipalProvider.get();
        if (parameter.isOptional() || parameter.getParameterType().equals(Optional.class)) {
            return Optional.ofNullable(requestPrincipal.getWorkspaceUser());
        }
        return requestPrincipal.getWorkspaceUser();
    }
}
