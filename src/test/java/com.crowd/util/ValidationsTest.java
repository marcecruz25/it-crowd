package com.crowd.util;

import com.crowd.model.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by marce on 3/22/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationsTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBeAllowed25Characters() {
        Word word = Word.builder().id_palabra(1).nombre("this is contain max words").build();
        assertEquals(true, Validations.isDataValid(word));
    }

    @Test
    public void shouldBeNotAllowedMoreThan25Characters() {
        Word word = Word.builder().id_palabra(1).nombre("this is contain max words1").build();
        assertEquals(false, Validations.isDataValid(word));
    }
}
