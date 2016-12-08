package criptext.com.countrypicker

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

/**
 * Created by gesuwall on 12/7/16.
 */

public class CountryUtils {

    companion object {
        /**
         * The drawable image name has the format "flag_$countryCode". We need to
         * load the drawable dynamically from country code. Code from
         * http://stackoverflow.com/
         * questions/3042961/how-can-i-get-the-resource-id-of
         * -an-image-if-i-know-its-name

         * @param drawableName
         * *
         * @return
         */
        fun getFlagResId(countryCode: String): Int {

            try {
                val res = R.drawable::class.java
                val field = res.getField("flag_$countryCode".toLowerCase(Locale.ENGLISH))
                val drawableId = field.getInt(null)
                return drawableId
            } catch (e: Exception) {
                Log.e("COUNTRYPICKER", "Failure to get drawable id.", e)
            }

            return R.drawable.flag_us
        }

        /**
         * Convenient function to read from raw file

         * @param context
         * *
         * @return
         * *
         * @throws java.io.IOException
         */
        @Throws(java.io.IOException::class)
        fun readFileAsString(context: Context, fileResId: Int): String {
            val inputStream = context.resources.openRawResource(fileResId)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val result = StringBuffer()
            var line: String? = null

            fun readNewLine(): String?{
                line = reader.readLine()
                return line
            }

            while (readNewLine() != null) {
                result.append(line)
            }

            reader.close()
            return result.toString()
        }

        /**
         * Get all countries with code and name from res/raw/countries.json

         * @return
         */
        fun getAllCountries(ctx: Context): List<Country>? {
            try {
                val newList = ArrayList<Country>()

                // Read from local file
                val allCountriesString = readFileAsString(ctx, R.raw.countries)
                val jsonArray = JSONArray(allCountriesString)

                for (i in 0..jsonArray.length() - 1) {
                    val jsonCountry = jsonArray.get(i) as JSONObject
                    val country = Country(
                        jsonCountry.getString("code"),
                        jsonCountry.getString("dial_code"),
                        jsonCountry.getString("name"))
                    newList.add(country)
                }

                // Return
                return newList

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    }

}
