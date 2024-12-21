package com.tek_up.elearning.config;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
