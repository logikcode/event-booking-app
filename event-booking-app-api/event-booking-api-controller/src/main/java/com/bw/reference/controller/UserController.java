package com.bw.reference.controller;

import com.bw.commons.authclient.dto.ApiResourcePortalUser;
import com.bw.commons.security.constraint.Public;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.exception.ErrorResponse;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.response.handler.UserHandler;
import com.bw.reference.response.pojo.UserPojo;
import com.bw.reference.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Provider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Abbas Irekeola
 * @Email abbasirekeola@gmail.com
 * 10/1/21-9:39 PM
 */

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Provider<RequestPrincipal> requestPrincipalProvider;

    private final UserHandler userHandler;
    private final KeycloakService keycloakService;


    @GetMapping("/me")
    public UserPojo userDetails() {
        WorkspaceUser workspaceUser = requestPrincipalProvider.get().getWorkspaceUser();
        if (workspaceUser == null) {
            throw new ErrorResponse(401, "User not found",null);
        }
        return userHandler.getUserPojo(workspaceUser);
    }


    @Public
    @PostMapping("/logout")
    public ResponseEntity logoutUser(HttpServletResponse response) {
        Cookie cookie = new Cookie(RequestPrincipal.AUTH_TOKEN_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        keycloakService.logout(requestPrincipalProvider.get().getWorkspaceUser());
        return ResponseEntity.ok().build();
    }

}
