package com.bw.reference.controller;

import com.bw.commons.security.constraint.Public;
import com.bw.reference.dao.account.WorkspaceUserRepository;
import com.bw.reference.domain.account.UserRegistrationDto;
import com.bw.reference.entity.WorkspaceUser;
import com.bw.reference.exception.ErrorResponse;
import com.bw.reference.principal.RequestPrincipal;
import com.bw.reference.response.handler.UserHandler;
import com.bw.reference.response.handler.UserRegistrationHandler;
import com.bw.reference.response.pojo.UserPojo;
import com.bw.reference.service.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Provider;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@Tag(name = "user registration", description = "User Registration API")
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    private final UserHandler userHandler;

    private final Provider<RequestPrincipal> requestPrincipalProvider;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final UserRegistrationHandler userRegistrationHandler;

    @Public
    @PostMapping("/users")
    @Operation(summary = "register user", description = "register a user with this API. Phone number verification code will be sent to user's phone")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "User Registered", content = @Content(schema = @Schema(implementation = UserPojo.class))), @ApiResponse(responseCode = "400", description = "Bad Request")})
    public ResponseEntity<UserPojo> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationHandler.registerUser(userRegistrationDto));
    }


    @PostMapping("/email-verification")
    @Operation(summary = "request email verification", description = "Request email Verification")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Request email Verification")})
    public ResponseEntity<com.bw.commons.apiclient.ApiResponse<String>> requestEmailVerification() {
        WorkspaceUser workspaceUser = requestPrincipalProvider.get().getWorkspaceUser();
//        emailNotificationService.sendEmailVerification(portalUser);
        return ResponseEntity.ok(new com.bw.commons.apiclient.ApiResponse<>(200, HttpStatus.OK.toString(), "SENT"));
    }

    @Public
    @GetMapping("/users/{userId}/verify-email")
    @Operation(summary = "verify email", description = "verify email")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "verify email")})
    public ResponseEntity<com.bw.commons.apiclient.ApiResponse<String>> verifyEmail(@PathVariable("userId")
                                                                                            String userId) {
        WorkspaceUser workspaceUser = workspaceUserRepository.findByUserId(userId).orElseThrow(() -> new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User not found", null));
        userRegistrationService.verifyEmail(workspaceUser);
        return ResponseEntity.ok(new com.bw.commons.apiclient.ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Email successfully verified"));
    }


}
