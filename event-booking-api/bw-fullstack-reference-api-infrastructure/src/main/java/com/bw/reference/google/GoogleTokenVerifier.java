package com.bw.reference.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * @author Neme Iloeje niloeje@byteworks.com.ng
 */
@Component
public class GoogleTokenVerifier {

    @Autowired
    private Environment env;

    public GoogleIdToken.Payload verifyToken(String idTokenString)
            throws IllegalArgumentException {

        final GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(env.getProperty("google.client.id")))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = googleIdTokenVerifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        if (idToken == null) {
            throw new IllegalArgumentException("idToken is invalid");
        }

        return idToken.getPayload();
    }
}
