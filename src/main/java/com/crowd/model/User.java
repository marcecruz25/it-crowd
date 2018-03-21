package com.crowd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private int age;
}
