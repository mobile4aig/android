package com.aig.android.interview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SocialNetworkFeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public enum SocialNetworkFeed {
        UNDEFINED,
        FACEBOOK,
        TWITTER
    }

    static SocialNetworkFeed s_currentSocialNetworkFeed = SocialNetworkFeed.UNDEFINED;

    WebView mFacebookWebView;
    WebView mTwitterWebView;
    FloatingActionButton mlogoButton;
    RelativeLayout mButtonPannel;
    Button mFacebookButton;
    Button mTwitterButton;

    static SocialNetworkFeedActivity s_socialNetworkFeedActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_network_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s_currentSocialNetworkFeed == SocialNetworkFeed.FACEBOOK) {
                    Snackbar.make(view, "Feed from Facebook", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Feed from Twitter", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mButtonPannel = (RelativeLayout) findViewById(R.id.button_panel);
        mFacebookButton = (Button) findViewById(R.id.button_facebook);
        mTwitterButton = (Button) findViewById(R.id.button_twitter);

        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFeed(SocialNetworkFeed.FACEBOOK);
            }
        });

        mTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFeed(SocialNetworkFeed.TWITTER);
            }
        });

        mFacebookWebView = (WebView) findViewById(R.id.facebook_webview);
        mTwitterWebView = (WebView) findViewById(R.id.twitter_webview);
        mlogoButton = (FloatingActionButton) findViewById(R.id.fab);

        WebSettings webSettings = mFacebookWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings = mTwitterWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mFacebookWebView.loadUrl("https://m.facebook.com/");
        mFacebookWebView.setWebViewClient(new WebViewClient());

        mTwitterWebView.loadUrl("https://m.twitter.com/");
        mTwitterWebView.setWebViewClient(new WebViewClient());

        s_socialNetworkFeedActivity = this;

        switch (s_currentSocialNetworkFeed) {
            case UNDEFINED:
                mFacebookWebView.setVisibility(View.INVISIBLE);
                mTwitterWebView.setVisibility(View.INVISIBLE);
                mlogoButton.setVisibility(View.INVISIBLE);
                break;
            case FACEBOOK:
                mButtonPannel.setVisibility(View.GONE);
                mlogoButton.setImageResource(R.drawable.facebook);
                mlogoButton.setVisibility(View.VISIBLE);
                mFacebookWebView.setVisibility(View.VISIBLE);
                mTwitterWebView.setVisibility(View.INVISIBLE);
                break;
            case TWITTER:
                mButtonPannel.setVisibility(View.GONE);
                mlogoButton.setImageResource(R.drawable.twitter);
                mlogoButton.setVisibility(View.VISIBLE);
                mFacebookWebView.setVisibility(View.INVISIBLE);
                mTwitterWebView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.social_network_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_facebook) {
            switchFeed(SocialNetworkFeed.FACEBOOK);
            return true;
        }
        if (id == R.id.action_twitter) {
            switchFeed(SocialNetworkFeed.TWITTER);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_facebook) {
            SocialNetworkFeedActivity.switchFeed(SocialNetworkFeed.FACEBOOK);
        } else if (id == R.id.nav_twitter) {
            SocialNetworkFeedActivity.switchFeed(SocialNetworkFeed.TWITTER);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void switchFeed(SocialNetworkFeed socialNetwork) {

        if (socialNetwork == s_currentSocialNetworkFeed) {
            return;
        }

        if (s_socialNetworkFeedActivity != null) {
            switch (socialNetwork) {
                case UNDEFINED:
                    s_socialNetworkFeedActivity.mFacebookWebView.setVisibility(View.INVISIBLE);
                    s_socialNetworkFeedActivity.mTwitterWebView.setVisibility(View.INVISIBLE);
                    s_socialNetworkFeedActivity.mlogoButton.setVisibility(View.INVISIBLE);
                    break;
                case FACEBOOK:
                    s_socialNetworkFeedActivity.mButtonPannel.setVisibility(View.GONE);
                    s_socialNetworkFeedActivity.mFacebookWebView.setVisibility(View.VISIBLE);
                    s_socialNetworkFeedActivity.mTwitterWebView.setVisibility(View.INVISIBLE);
                    s_socialNetworkFeedActivity.mlogoButton.setImageResource(R.drawable.facebook);
                    s_socialNetworkFeedActivity.mlogoButton.setVisibility(View.VISIBLE);
                    break;
                case TWITTER:
                    s_socialNetworkFeedActivity.mButtonPannel.setVisibility(View.GONE);
                    s_socialNetworkFeedActivity.mFacebookWebView.setVisibility(View.INVISIBLE);
                    s_socialNetworkFeedActivity.mTwitterWebView.setVisibility(View.VISIBLE);
                    s_socialNetworkFeedActivity.mlogoButton.setImageResource(R.drawable.twitter);
                    s_socialNetworkFeedActivity.mlogoButton.setVisibility(View.VISIBLE);
                    break;
            }
        }
        s_currentSocialNetworkFeed = socialNetwork;

    }
}
