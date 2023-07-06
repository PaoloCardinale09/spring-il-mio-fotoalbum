package org.lessons.springilmiofotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Size(max = 255, message = "The description must be a maximum of 255 characters")
    @NotBlank(message = "Description must not be null or blank")
    private String description;
    @NotBlank(message = "pics url must not be null or blank")
    @Column(length = 1000)
    @Size(max = 1000, message = "The Url must be a maximum of 1000 characters")
    private String url;
    private Boolean visible;
}
