package ru.alexferz.redbot.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(schema = "redbot", name = "quote")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String quote;

    @Column(name = "add_username")
    String addUsername;

    @Column(name = "create_dt")
    LocalDateTime createDt;

    @Column(name = "last_usage")
    LocalDateTime lastUsageDt;

    @PrePersist
    public void prePersist() {
        if (createDt == null) {
            createDt = LocalDateTime.now();
        }
    }
}
