/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package ch.zeromail.a2ndmap.bluetoothchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ch.zeromail.a2ndmap.common.activities.SampleActivityBase;
import ch.zeromail.a2ndmap.common.logger.Log;
import ch.zeromail.a2ndmap.common.logger.LogFragment;
import ch.zeromail.a2ndmap.common.logger.LogWrapper;
import ch.zeromail.a2ndmap.common.logger.MessageOnlyLogFilter;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            String id = "";

            BluetoothChatFragment fragment = new BluetoothChatFragment(id);

            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();

        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        BluetoothChatFragment fragment = (BluetoothChatFragment) getSupportFragmentManager().findFragmentById(R.id.sample_content_fragment);
        Uri data = intent.getData();
        if (data != null) {
            fragment.updateMessage(data.toString());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
