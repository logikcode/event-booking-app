package com.bw.reference.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Gibah Joseph
 * email: gibahjoe@gmail.com
 * Sep, 2020
 **/
@Getter
@Setter
public class AuthUserDto {
    private String id;
    private String userName;
    private String authToken;
}
