/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lq.textclock.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.AttributeSet;

import androidx.annotation.InspectableProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.view.ViewDebug.ExportedProperty;


public class TextClock extends androidx.appcompat.widget.AppCompatTextView {

    private CharSequence mFormat24;
    private CharSequence mDescFormat24;
    private SimpleDateFormat simpleDateFormat;
    private boolean mRegistered;
    private boolean mShouldRunTicker;

    private Calendar mTime;
    private String mTimeZone;
    private boolean mHasSeconds = true;

    // Used by tests to stop time change events from triggering the text update
    private boolean mStopTicking;
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mStopTicking) {
                return; // Test disabled the clock ticks
            }
            if (mTimeZone == null && Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                final String timeZone = intent.getStringExtra(Intent.EXTRA_TIMEZONE);
                createTime(timeZone);
            } else if (!mShouldRunTicker && (Intent.ACTION_TIME_TICK.equals(intent.getAction())
                    || Intent.ACTION_TIME_CHANGED.equals(intent.getAction()))) {
                return;
            }
            onTimeChanged();
        }
    };

    private final Runnable mTicker = new Runnable() {
        public void run() {
            if (mStopTicking) {
                return; // Test disabled the clock ticks
            }
            onTimeChanged();

            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            Handler handler = getHandler();
            if (handler != null) {
                handler.postAtTime(mTicker, next);
            }
        }
    };


    @SuppressWarnings("UnusedDeclaration")
    public TextClock(Context context) {
        this(context,null);
        init();
    }

    @SuppressWarnings("UnusedDeclaration")
    public TextClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFormat24 = "dd MMM yyyy HH:mm:ss";
        mTimeZone = "Australia/Sydney";
        simpleDateFormat = new SimpleDateFormat(mFormat24.toString(),Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        init();
    }


    private static CharSequence abc(CharSequence a, CharSequence b, CharSequence c) {
        return a == null ? (b == null ? c : b) : a;
    }

    private void init() {

        createTime(mTimeZone);
        chooseFormat();
    }

    private void createTime(String timeZone) {
        if (timeZone != null) {
            mTime = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        } else {
            mTime = Calendar.getInstance();
        }
    }


    /**
     * Returns the formatting pattern used to display the date and/or time
     * in 24-hour mode. The formatting pattern syntax is described in
     * {@link DateFormat}.
     *
     * @return A {@link CharSequence} or null.
     * @see #setFormat24Hour(CharSequence)
     * @see #()
     */
    @InspectableProperty
    @ExportedProperty
    public CharSequence getFormat24Hour() {
        return mFormat24;
    }

    /**
     * <p>Specifies the formatting pattern used to display the date and/or time
     * in 24-hour mode. The formatting pattern syntax is described in
     * {@link DateFormat}.</p>
     *
     * <p>If this pattern is set to null, {@link #getFormat24Hour()} will be used
     * even in 12-hour mode. If both 24-hour and 12-hour formatting patterns
     * are set to null, the default pattern for the current locale will be used
     * instead.</p>
     *
     * <p><strong>Note:</strong> if styling is not needed, it is highly recommended
     * you supply a format string generated by
     * {@link DateFormat#getBestDateTimePattern(java.util.Locale, String)}. This method
     * takes care of generating a format string adapted to the desired locale.</p>
     *
     * @param format A date/time formatting pattern as described in {@link DateFormat}
     * @attr ref android.R.styleable#TextClock_format24Hour
     * @see #getFormat24Hour()
     * @see #()
     * @see DateFormat#getBestDateTimePattern(java.util.Locale, String)
     * @see DateFormat
     */
    public void setFormat24Hour(CharSequence format) {
        mFormat24 = format;

        chooseFormat();
        onTimeChanged();
    }

    /**
     * Update the displayed time if necessary and invalidate the view.
     */
    public void refreshTime() {
        onTimeChanged();
        invalidate();
    }


    /**
     * Indicates which time zone is currently used by this view.
     *
     * @return The ID of the current time zone or null if the default time zone,
     * as set by the user, must be used
     * @see TimeZone
     * @see java.util.TimeZone#getAvailableIDs()
     * @see #setTimeZone(String)
     */
    @InspectableProperty
    public String getTimeZone() {
        return mTimeZone;
    }

    /**
     * Sets the specified time zone to use in this clock. When the time zone
     * is set through this method, system time zone changes (when the user
     * sets the time zone in settings for instance) will be ignored.
     *
     * @param timeZone The desired time zone's ID as specified in {@link TimeZone}
     *                 or null to user the time zone specified by the user
     *                 (system time zone)
     * @attr ref android.R.styleable#TextClock_timeZone
     * @see #getTimeZone()
     * @see java.util.TimeZone#getAvailableIDs()
     * @see TimeZone#getTimeZone(String)
     */
    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        createTime(timeZone);
        onTimeChanged();
    }

    /**
     * Selects either one of {@link #()} or {@link #getFormat24Hour()}
     * depending on whether the user has selected 24-hour format.
     */
    private void chooseFormat() {
        boolean hadSeconds = mHasSeconds;
        if (mShouldRunTicker && hadSeconds != mHasSeconds) {
            if (hadSeconds) getHandler().removeCallbacks(mTicker);
            else mTicker.run();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!mRegistered) {
            mRegistered = true;

            registerReceiver();

            createTime(mTimeZone);
        }
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);

        if (!mShouldRunTicker && isVisible) {
            mShouldRunTicker = true;
            if (mHasSeconds) {
                mTicker.run();
            } else {
                onTimeChanged();
            }
        } else if (mShouldRunTicker && !isVisible) {
            mShouldRunTicker = false;
            getHandler().removeCallbacks(mTicker);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mRegistered) {
            unregisterReceiver();

            mRegistered = false;
        }
    }

    private void registerReceiver() {
        final IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);

        // OK, this is gross but needed. This class is supported by the
        // remote views mechanism and as a part of that the remote views
        // can be inflated by a context for another user without the app
        // having interact users permission - just for loading resources.
        // For example, when adding widgets from a managed profile to the
        // home screen. Therefore, we register the receiver as the user
        // the app is running as not the one the context is for.
        getContext().registerReceiver(mIntentReceiver,filter,null,getHandler());
//        getContext().registerReceiverAsUser(mIntentReceiver, android.os.Process.myUserHandle(),
//                filter, null, getHandler());
    }


    private void unregisterReceiver() {
        getContext().unregisterReceiver(mIntentReceiver);
    }

    /**
     * Update the displayed time if this view and its ancestors and window is visible
     */
    private void onTimeChanged() {
        mTime.setTimeInMillis(System.currentTimeMillis());
        setText(simpleDateFormat.format(mTime.getTimeInMillis()));
        setContentDescription(simpleDateFormat.format(mTime.getTimeInMillis()));
    }
}
