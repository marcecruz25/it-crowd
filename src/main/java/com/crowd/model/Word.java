package com.crowd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by marce on 3/21/18.
 */
@Entity
@Table(name = "palabra")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Word implements Comparable<Word> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id_palabra;

    @Column
    private String nombre;

    @Override
    public int compareTo(Word word) {
        return this.nombre.compareTo(word.nombre);
    }
}
