/*
 * Copyright (C) 2016 The Supower Open Source Project
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

package tw.supra.lib.supower.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;


/**
 * Interface for accessing and modifying preference data returned by {@link
 * Context#getSharedPreferences}.  For any particular set of preferences,
 * there is a single instance of this class that all clients share.
 * Modifications to the preferences must go through an {@link SharedPreferences.Editor} object
 * to ensure the preference values remain in a consistent state and control
 * when they are committed to storage.  Objects that are returned from the
 * various <code>get</code> methods must be treated as immutable by the application.
 * <p/>
 * <p><em>Note: currently this class does not support use across multiple
 * processes.  This will be added later.</em>
 * <p/>
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For more information about using SharedPreferences, read the
 * <a href="{@docRoot}guide/topics/data/data-storage.html#pref">Data Storage</a>
 * developer guide.</p></div>
 *
 * @see Context#getSharedPreferences
 * Created by supra on 16-1-11.
 */
public final class SmartPreferences {

    public final Context mCtx;
    public final String mName;
    private WeakReference<SharedPreferences> mWeakPref;
    private WeakReference<SharedPreferences.Editor> mWeakEditor;
    private int mMode;

    private SmartPreferences(Context ctx, String name, int mode) {
        mCtx = ctx.getApplicationContext();
        mName = name;
        mMode = mode;
    }

    /**
     * Retrieve and hold the contents of the preferences file 'name', returning
     * a SharedPreferences through which you can retrieve and modify its
     * values.  Only one instance of the SharedPreferences object is returned
     * to any callers for the same name, meaning they will see each other's
     * edits as soon as they are made.
     * @param ctx 上下文
     * @param name Desired preferences file. If a preferences file by this name
     *             does not exist, it will be created when you retrieve an
     *             editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
     * @param mode Operating mode.  Use 0 or {@link Context#MODE_PRIVATE} for the
     *             default operation, {@link Context#MODE_WORLD_READABLE}
     *             and {@link Context#MODE_WORLD_WRITEABLE} to control permissions.  The bit
     *             {@link Context#MODE_MULTI_PROCESS} can also be used if multiple processes
     *             are mutating the same SharedPreferences file.  {@link Context#MODE_MULTI_PROCESS}
     *             is always on in apps targeting Gingerbread (Android 2.3) and below, and
     *             off by default in later versions.
     * @return The single {@link SmartPreferences} instance that can be used
     * to retrieve and modify the preference values.
     * @see Context#MODE_PRIVATE
     * @see Context#MODE_WORLD_READABLE
     * @see Context#MODE_WORLD_WRITEABLE
     * @see Context#MODE_MULTI_PROCESS
     */
    public static SmartPreferences get(Context ctx, String name, int mode) {
        return new SmartPreferences(ctx, name, mode);
    }

    /**
     * Retrieve and hold the contents of the preferences file 'name', returning
     * a SharedPreferences through which you can retrieve and modify its
     * values.  Only one instance of the SharedPreferences object is returned
     * to any callers for the same name, meaning they will see each other's
     * edits as soon as they are made.
     *
     * @return The single {@link SharedPreferences} instance that can be used
     * to retrieve and modify the preference values.
     */
    public SharedPreferences getSharedPreferences() {
        if (null == mWeakPref || null == mWeakPref.get()) {
            mWeakPref = new WeakReference<SharedPreferences>(mCtx.getSharedPreferences(mName, mMode));
        }
        return mWeakPref.get();
    }

    /**
     * Create a new Editor for these preferences, through which you can make
     * modifications to the data in the preferences and atomically commit those
     * changes back to the SharedPreferences object.
     * <p/>
     * <p>Note that you <em>must</em> call {@link SharedPreferences.Editor#commit} to have any
     * changes you perform in the Editor actually show up in the
     * SharedPreferences.
     *
     * @return Returns a new instance of the {@link SharedPreferences.Editor} interface, allowing
     * you to modify the values in this SharedPreferences object.
     */
    private SharedPreferences.Editor edit() {
        if (null == mWeakEditor || null == mWeakEditor.get()) {
            mWeakEditor = new WeakReference<SharedPreferences.Editor>(getSharedPreferences().edit());
        }
        return mWeakEditor.get();
    }

    /**
     * Set a String value in the preferences editor, to be written back once
     * {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.  Supplying {@code null}
     *              as the value is equivalent to calling {@link #remove(String)} with
     *              this key.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putString(String key, String value) {
        edit().putString(key, value).apply();
        return this;
    }

    /**
     * Set a set of String values in the preferences editor, to be written
     * back once {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} is called.
     *
     * @param key    The name of the preference to modify.
     * @param values The set of new values for the preference.  Passing {@code null}
     *               for this argument is equivalent to calling {@link #remove(String)} with
     *               this key.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putStringSet(String key, Set<String> values) {
        edit().putStringSet(key, values).apply();
        return this;
    }

    /**
     * Set an int value in the preferences editor, to be written back once
     * {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putInt(String key, int value) {
        edit().putInt(key, value).apply();
        return this;
    }

    /**
     * Set a long value in the preferences editor, to be written back once
     * {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putLong(String key, long value) {
        edit().putLong(key, value).apply();
        return this;
    }

    /**
     * Set a float value in the preferences editor, to be written back once
     * {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putFloat(String key, float value) {
        edit().putFloat(key, value).apply();
        return this;
    }

    /**
     * Set a boolean value in the preferences editor, to be written back
     * once {@link SharedPreferences.Editor#commit} or {@link SharedPreferences.Editor#apply} are called.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences putBoolean(String key, boolean value) {
        edit().putBoolean(key, value).apply();
        return this;
    }

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once {@link SharedPreferences.Editor#commit} is
     * called.
     * <p/>
     * <p>Note that when committing back to the preferences, all removals
     * are done first, regardless of whether you called remove before
     * or after put methods on this editor.
     *
     * @param key The name of the preference to remove.
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences remove(String key) {
        edit().remove(key).commit();
        return this;
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.  Once commit is called, the only remaining preferences
     * will be any that you have defined in this editor.
     * <p/>
     * <p>Note that when committing back to the preferences, the clear
     * is done first, regardless of whether you called clear before
     * or after put methods on this editor.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    public SmartPreferences clear() {
        edit().clear().commit();
        return this;
    }

    /**
     * Retrieve all values from the preferences.
     * <p/>
     * <p>Note that you <em>must not</em> modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     * @throws NullPointerException
     */
    public Map<String, ?> getAll() {
        return getSharedPreferences().getAll();
    }

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     * @throws ClassCastException
     */
    public String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

    /**
     * Retrieve a set of String values from the preferences.
     * <p/>
     * <p>Note that you <em>must not</em> modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key       The name of the preference to retrieve.
     * @param defValues Values to return if this preference does not exist.
     * @return Returns the preference values if they exist, or defValues.
     * Throws ClassCastException if there is a preference with this name
     * that is not a Set.
     * @throws ClassCastException
     */
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return getSharedPreferences().getStringSet(key, defValues);
    }

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     * @throws ClassCastException
     */
    public int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     * @throws ClassCastException
     */
    public long getLong(String key, long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     * @throws ClassCastException
     */
    public float getFloat(String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key      The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     * @throws ClassCastException
     */
    public boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }

    /**
     * Registers a callback to be invoked when a change happens to a preference.
     * <p/>
     * <p class="caution"><strong>Caution:</strong> The preference manager does
     * not currently store a strong reference to the listener. You must store a
     * strong reference to the listener, or it will be susceptible to garbage
     * collection. We recommend you keep a reference to the listener in the
     * instance data of an object that will exist as long as you need the
     * listener.</p>
     *
     * @param l The callback that will run.
     * @see #unregisterOnSharedPreferenceChangeListener
     */
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener l) {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(l);
    }

    /**
     * Unregisters a previous callback.
     *
     * @param l The callback that should be unregistered.
     * @see #registerOnSharedPreferenceChangeListener
     */
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener l) {
        getSharedPreferences().unregisterOnSharedPreferenceChangeListener(l);
    }
}
