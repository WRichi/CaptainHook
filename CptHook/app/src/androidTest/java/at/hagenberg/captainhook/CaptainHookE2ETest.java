package at.hagenberg.captainhook;

import android.app.Application;
import android.app.Instrumentation;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.hagenberg.captainhook.model.CaptainHookRepository;
import at.hagenberg.captainhook.model.spotify.SpotifySearchResultCallback;
import at.hagenberg.captainhook.model.spotify.SpotifySearchType;
import at.hagenberg.captainhook.model.spotify.spotify_model.Artist_;
import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;
import at.hagenberg.captainhook.model.spotify.spotify_model.track.Item;
import at.hagenberg.captainhook.model.youtube.YoutubeCallback;
import at.hagenberg.captainhook.viewmodels.SpotifyViewModel;
import at.hagenberg.captainhook.viewmodels.YoutubeViewModel;
import at.hagenberg.captainhook.views.SpotifySearchActivity;
import at.hagenberg.captainhook.views.YoutubeBrowseActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import at.hagenberg.captainhook.R;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
    @RunWith(AndroidJUnit4.class)
    @LargeTest
    public class CaptainHookE2ETest {

        private String stringToBetyped;

        @Rule
        public ActivityTestRule<SpotifySearchActivity> activityRule
                = new ActivityTestRule<>(SpotifySearchActivity.class);

        @Before
        public void initValidString() {
            // Specify a valid string.
        }
    @Test
    public void testSpotifySearch() {
        final String query = "In the End Linkin Park";
        final long waitTimeMillis = 3000;
        
        onView(withId(R.id.spotify_search_edittext))
                .perform(typeText(query));
        onView(withId(R.id.spotify_search_button)).perform(click());

        onView(isRoot()).perform(waitFor(waitTimeMillis));

        onView(withId(R.id.spotify_recyclerview)).perform(actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(waitFor(waitTimeMillis));

        onView(withId(R.id.youtube_recyclerview)).perform(actionOnItemAtPosition(0, click()));

        onView(isRoot()).perform(waitFor(waitTimeMillis));
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

}

