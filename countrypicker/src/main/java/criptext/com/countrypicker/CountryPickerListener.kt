package criptext.com.countrypicker

/**
 * Created by gesuwall on 12/7/16.
 */

interface CountryPickerListener {
    fun onSelectCountry(code: String, dial_code: String, name: String)
}
