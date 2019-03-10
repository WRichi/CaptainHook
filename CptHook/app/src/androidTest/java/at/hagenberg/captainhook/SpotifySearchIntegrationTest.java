package at.hagenberg.captainhook;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import at.hagenberg.captainhook.model.CaptainHookRepository;
import at.hagenberg.captainhook.model.spotify.SpotifySearchResultCallback;
import at.hagenberg.captainhook.model.spotify.SpotifySearchType;
import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;
import at.hagenberg.captainhook.viewmodels.SpotifyViewModel;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SpotifySearchIntegrationTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        final String query = "In the End Linkin Park";
        final SpotifySearchType searchType = SpotifySearchType.TRACK;

        final Context appContext = InstrumentationRegistry.getTargetContext().getApplicationContext();

        CaptainHookRepository captainHookRepository = new CaptainHookRepository((Application) appContext);
        captainHookRepository.searchSpotify(query, searchType, new SpotifySearchResultCallback() {
            @Override
            public void onSearchResult(SpotifyData spotifyData) {
                assertEquals("at.hagenberg.captainhook", appContext.getPackageName());
            }
        });
    }
}
