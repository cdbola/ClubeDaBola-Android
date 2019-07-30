package cdbol.br.com.clubedabola.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson




class PreferencesString private constructor(context: Context) {
    private val mPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null
    private var mBulkUpdate = false

    /**
     * Class for keeping all the keys used for shared preferences in one place.
     */
    object Key {
        val SAMPLE = "a_sample_key"

    }

    init {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, `val`: String) {
        doEdit()
        mEditor!!.putString(key, `val`)
        doCommit()
    }

    fun putObj(key: String, `val`: Any) {
        doEdit()
        val gson = Gson()
        val json = gson.toJson(`val`)
        mEditor!!.putString(key, json)
        doCommit()
    }

    fun put(key: String, `val`: Int) {
        doEdit()
        mEditor!!.putInt(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Boolean) {
        doEdit()
        mEditor!!.putBoolean(key, `val`)
        doCommit()
    }

    fun put(key: String, `val`: Float) {
        doEdit()
        mEditor!!.putFloat(key, `val`)
        doCommit()
    }

    /**
     * Convenience method for storing doubles.
     *
     *
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to store.
     * @param val The new value for the preference.
     */
    fun put(key: String, `val`: Double) {
        doEdit()
        mEditor!!.putString(key, `val`.toString())
        doCommit()
    }

    fun put(key: String, `val`: Long) {
        doEdit()
        mEditor!!.putLong(key, `val`)
        doCommit()
    }

    fun getString(key: String, defaultValue: String): String? {
        return mPref.getString(key, defaultValue)
    }

    fun getString(key: String): String? {
        return mPref.getString(key, null)
    }

    fun getInt(key: String): Int {
        return mPref.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mPref.getInt(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return mPref.getLong(key, 0)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return mPref.getLong(key, defaultValue)
    }

    fun getFloat(key: String): Float {
        return mPref.getFloat(key, 0f)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return mPref.getFloat(key, defaultValue)
    }

    fun getObj(key:String, obj: Any) : Any? {
        val gson = Gson()
        val json = mPref.getString(key, "")
        return gson.fromJson(json, obj::class.java)
    }
    /**
     * Convenience method for retrieving doubles.
     *
     *
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The name of the preference to fetch.
     */
    @JvmOverloads
    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        try {
            return java.lang.Double.valueOf(mPref.getString(key, defaultValue.toString()))!!
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }

    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mPref.getBoolean(key, defaultValue)
    }

    fun getBoolean(key: String): Boolean {
        return mPref.getBoolean(key, false)
    }

    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The name of the key(s) to be removed.
     */
    fun remove(vararg keys: String) {
        doEdit()
        for (key in keys) {
            mEditor!!.remove(key)
        }
        doCommit()
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    fun clear() {
        doEdit()
        mEditor!!.clear()
        doCommit()
    }

    fun edit() {
        mBulkUpdate = true
        mEditor = mPref.edit()
    }

    fun commit() {
        mBulkUpdate = false
        mEditor!!.commit()
        mEditor = null
    }

    private fun doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit()
        }
    }

    private fun doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor!!.commit()
            mEditor = null
        }
    }

    companion object {
        // TODO: CHANGE THIS TO SOMETHING MEANINGFUL
        private val SETTINGS_NAME = "PREFERENCE_NAME"
        private var sSharedPrefs: PreferencesString? = null


        fun getInstance(context: Context): PreferencesString {
            if (sSharedPrefs == null) {
                sSharedPrefs = PreferencesString(context.getApplicationContext())
            }
            return sSharedPrefs as PreferencesString
        }

        //Option 1:
        //Option 2:
        // Alternatively, you can create a new instance here
        // with something like this:
        // getInstance(MyCustomApplication.getAppContext());
        val instance: PreferencesString
            get() {
                if (sSharedPrefs != null) {
                    return sSharedPrefs as PreferencesString
                }
                throw IllegalArgumentException("Should use getInstance(Context) at least once before using this method.")
            }
    }
}
/**
 * Convenience method for retrieving doubles.
 *
 *
 * There may be instances where the accuracy of a double is desired.
 * SharedPreferences does not handle doubles so they have to
 * cast to and from String.
 *
 * @param key The name of the preference to fetch.
 */