package com.tek_up.gestionStock.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "courses")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cours_id")
    private Long id;

    @Column()
    @NotEmpty
    private String name;

    @Column()
    @NotEmpty
    private String description;

    @Column()
    @NotEmpty
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursBooking> bookings = new ArrayList<>();
}
