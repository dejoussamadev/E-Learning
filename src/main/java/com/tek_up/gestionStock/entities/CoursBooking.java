package com.tek_up.gestionStock.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "cours_booking")
public class CoursBooking {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    private LocalDateTime creationDate;
}
