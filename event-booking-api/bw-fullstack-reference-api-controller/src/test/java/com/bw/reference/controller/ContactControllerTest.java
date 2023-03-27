package com.bw.reference.controller;

import com.bw.reference.entity.Contact;
import com.bw.reference.test.WebIntegrationTest;
import com.google.gson.reflect.TypeToken;
import com.querydsl.core.QueryResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Abbas Irekeola
 * @Email abbasirekeola@gmail.com
 * 10/31/21-7:35 AM
 */
@Profile("test")
public class ContactControllerTest extends WebIntegrationTest {

    List<Contact> contacts;
    @BeforeEach
    void setUp() {
        contacts = modelFactory.create(com.bw.reference.entity.Contact.class, 10);
    }


    @Test
    void searchContacts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    QueryResults<Contact> contactQueryResults = gson.fromJson(response, new TypeToken<QueryResults<Contact>>() {
                    }.getType());
                    assertNotNull(contactQueryResults);
                    assertFalse(contactQueryResults.isEmpty());
                    assertEquals(10, contactQueryResults.getResults().size());
                });
    }

}
