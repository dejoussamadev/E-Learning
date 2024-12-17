package com.tek_up.gestionStock.config;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String fullName;
    private String email;
    private Boolean active;

    public JwtResponse(String token, String fullName, Long id, String email, Boolean active) {
        this.token = token;
        this.fullName = fullName;
        this.id=id;
        this.email = email;
        this.active = active;
    }

}
