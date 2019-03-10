package at.hagenberg.captainhook;

import org.junit.Test;

import java.text.ParseException;

import static at.hagenberg.captainhook.views.YoutubeBrowseActivity.getDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UnitTests {

    @Test
    public void passYtDateStringAndGetFormattedDateString() {
        String publishedAt = "2019-03-15T16:25:48.000Z";
        try {
            String dateString = getDate(publishedAt);
            System.out.println(dateString);
        } catch (ParseException e) {
            System.out.println(e);
            fail();
        }
        assertTrue(true);
    }
}
