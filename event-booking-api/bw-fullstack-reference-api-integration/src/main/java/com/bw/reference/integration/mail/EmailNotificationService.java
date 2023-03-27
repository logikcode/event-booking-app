package com.bw.reference.integration.mail;

import com.bw.reference.entity.WorkspaceUser;

import java.util.List;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */

public interface EmailNotificationService {
    void sendEmailVerification(WorkspaceUser workspaceUser);

    void sendForgotPassword(String email);
}