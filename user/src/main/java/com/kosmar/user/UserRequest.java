package com.kosmar.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
}
