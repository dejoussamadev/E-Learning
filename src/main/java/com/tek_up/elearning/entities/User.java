package com.tek_up.elearning.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tek_up.elearning.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column()
    @NotEmpty
    private String firstName;

    @Column()
    @NotEmpty
    private String lastName;

    @Column()
    @NotEmpty
    private String birthDate;

    @Column()
    @NotEmpty
    private String email;

    @Column()
    @NotEmpty
    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING) // Ensure this is stored as a string
    private Roles role;

    @Column()
    private Boolean isEnabled;

    @Column()
    private Date createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Cours> courses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursBooking> bookings = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Ensure roles are converted to SimpleGrantedAuthority with ROLE_ prefix
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;  // Return the email (or other unique identifier) as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // You can implement expiration logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // You can implement lock logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // You can implement credential expiration logic if needed
    }

    @Override
    public boolean isEnabled() {
        return isEnabled != null && isEnabled;  // Return actual isEnabled field value
    }
}