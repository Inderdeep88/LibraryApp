package org.fullstack.libapp.responsemodels;

import lombok.Data;

@Data
public class LoginResponse {
    String status;
    String token;
}
