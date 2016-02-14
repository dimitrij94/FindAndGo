package com.example;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dmitrij on 14.02.2016.
 */

public class PatternTest {
    @Test
    public void voidCheckUsernamePattern() {
        String userNameRegexp = "^[a-zA-Z][a-zA-Z0-9_]{1,40}$";
        Pattern usernamePattern = Pattern.compile(userNameRegexp);
        Matcher matcher = usernamePattern.matcher("dimitrij94");
        assertEquals("must be true", true, matcher.matches());
        matcher = usernamePattern.matcher("94dimitrij");
        assertEquals("must be false",false, matcher.matches());
    }
}
