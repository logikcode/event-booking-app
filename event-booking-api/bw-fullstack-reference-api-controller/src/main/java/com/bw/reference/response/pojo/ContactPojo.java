package com.bw.reference.response.pojo;

import com.bw.enums.GenderConstant;
import com.bw.enums.GenericStatusConstant;
import com.bw.reference.entity.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ContactPojo {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private GenericStatusConstant status;
    //private String phoneNumber;

    //private GenderConstant gender;
    private Date dateCreated;

    public ContactPojo(Contact contact){
        id = contact.getId();
        firstName = contact.getFirstName();
        lastName = contact.getLastName();
        email = contact.getEmail();
        //phoneNumber = contact.getPhoneNumber();
        dateCreated = contact.getDateCreated();
        status = contact.getStatus();
    }


}
