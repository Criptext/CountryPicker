package criptext.com.countrypicker

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import java.util.*

/**
 * Created by gesuwall on 12/7/16.
 */

class CountryPicker : DialogFragment() {
    /**
     * View components
     */
    private var searchEditText: EditText? = null
    private var countryRecyclerView: RecyclerView? = null

    /**
     * Adapter for the listview
     */
    private var adapter: CountryListAdapter? = null

    /**
     * Hold all countries, sorted by country name
     */
    private var allCountriesList: List<Country>? = null

    val listener: CountryPickerListener?
        get() = activity as? CountryPickerListener

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate view
        val view = inflater!!.inflate(R.layout.country_picker, null)

        // Get countries from the json
        val _allCountriesList = CountryUtils.getAllCountries(activity)!!

        // Set dialog title if show as dialog
        val args = arguments
        if (args != null) {
            val dialogTitle = args.getString(DIALOG_TITLE_KEY)
            dialog.setTitle(dialogTitle)

        }

        // Get view components
        val _searchEditText = view.findViewById(R.id.country_picker_search) as EditText
        val _countryRecyclerView = view.findViewById(R.id.country_picker_recycler) as RecyclerView

        if (activity !is CountryPickerListener)
            throw IllegalStateException("The activity associated with the CountryPicker Dialog must " +
                "implement CountryPickerListener")
        // Set adapter
        adapter = CountryListAdapter(inflater, _allCountriesList, this)
        _countryRecyclerView.layoutManager = LinearLayoutManager(activity)


        // Search for which countries matched user query
        _searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s != null)
                    search(s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

        })

        searchEditText = _searchEditText
        countryRecyclerView = _countryRecyclerView
        allCountriesList = _allCountriesList

        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (countryRecyclerView?.adapter == null) {
                    countryRecyclerView?.adapter = adapter
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }

        })
        return view
    }

    private fun search(text: String) {
        val filteredList: List<Country>
        if(text.isEmpty())
            filteredList = allCountriesList!!
        else
            filteredList = allCountriesList!!.filter { it ->
                it.name.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase())
            }

        adapter!!.countries = filteredList
        adapter!!.notifyDataSetChanged()
    }

    companion object {
        val DIALOG_TITLE_KEY = "dialogTitle"

        fun newInstance(dialogTitle: String): CountryPicker {
            val picker = CountryPicker()
            val bundle = Bundle()
            bundle.putString(DIALOG_TITLE_KEY, dialogTitle)
            picker.arguments = bundle
            return picker
        }
    }

}
