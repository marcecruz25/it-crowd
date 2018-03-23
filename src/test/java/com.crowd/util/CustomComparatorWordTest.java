package com.crowd.util;

import com.crowd.model.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by marce on 3/22/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomComparatorWordTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBeSortedASC() {
        Word word1 = Word.builder().nombre("fuga").build();
        Word word2 = Word.builder().nombre("aereo").build();
        Word word3 = Word.builder().nombre("alerta1").build();
        Word word4 = Word.builder().nombre("alerta").build();
        Word word5 = Word.builder().nombre("opera").build();

        List<Word> wordList = new ArrayList<>();
        wordList.add(word1);
        wordList.add(word2);
        wordList.add(word3);
        wordList.add(word4);
        wordList.add(word5);

        Collections.sort(wordList, new CustomComparatorWord(false));

        assertEquals("opera", wordList.get(0).getNombre());
        assertEquals("fuga", wordList.get(1).getNombre());
        assertEquals("alerta1", wordList.get(2).getNombre());
        assertEquals("alerta", wordList.get(3).getNombre());
        assertEquals("aereo", wordList.get(4).getNombre());
    }

    @Test
    public void shouldBeSortedDESC() {
        Word word1 = Word.builder().nombre("fuga").build();
        Word word2 = Word.builder().nombre("aereo").build();
        Word word3 = Word.builder().nombre("alerta1").build();
        Word word4 = Word.builder().nombre("alerta").build();
        Word word5 = Word.builder().nombre("opera").build();
        List<Word> wordList = new ArrayList<>();

        wordList.add(word1);
        wordList.add(word2);
        wordList.add(word3);
        wordList.add(word4);
        wordList.add(word5);

        Collections.sort(wordList, new CustomComparatorWord(true));
        assertEquals("aereo", wordList.get(0).getNombre());
        assertEquals("alerta", wordList.get(1).getNombre());
        assertEquals("alerta1", wordList.get(2).getNombre());
        assertEquals("fuga", wordList.get(3).getNombre());
        assertEquals("opera", wordList.get(4).getNombre());
    }
}
