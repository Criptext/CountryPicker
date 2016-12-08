package criptext.com.countrypicker

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by gesuwall on 12/7/16.
 */

class CountryListAdapter(val inflater: LayoutInflater, var countries: List<Country>,
                         val dialog: CountryPicker): RecyclerView.Adapter<CountryListAdapter.CountryHolder>() {
    override fun getItemCount() = countries.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CountryHolder {
        val newView = inflater.inflate(R.layout.row, null)
        return CountryHolder(newView)
    }

    private fun finishDialog(country: Country) {
        dialog.dismiss()
        dialog.listener?.onSelectCountry(code = country.code,
                dial_code = country.dial_code, name = country.name)
    }

    override fun onBindViewHolder(holder: CountryHolder?, position: Int) {
        val country = countries[position]
        if (holder != null) {
            holder.setCountryName(country.name)
            holder.setFlagDrawable(CountryUtils.getFlagResId(country.code))
            holder.itemView.setOnClickListener {
                if(android.os.Build.VERSION.SDK_INT > 19) //wait for lollipop ripple
                    holder.itemView.postDelayed ({ finishDialog(country) }, 500L)
                else
                    finishDialog(country)
            }
        }
    }

    class CountryHolder: ViewHolder {
        private val nameView: TextView
        private val flagView: ImageView

        constructor(view: View) : super(getViewWithRecyclerLayoutParams(view)) {
            nameView = view.findViewById(R.id.row_title) as TextView
            flagView = view.findViewById(R.id.row_icon) as ImageView
        }

        fun setFlagDrawable(drawableId: Int) {
            flagView.setImageResource(drawableId)
        }

        fun setCountryName(name: String) {
            nameView.text = name
        }

        /**
         * Adds a RecyclerView.LayoutParams to a view
         * @param view view to set the new layout params
         * @return the view with a RecyclerView.LayoutParams object as its layout params
         *
         */
        companion object {
            private fun getViewWithRecyclerLayoutParams(view: View): View {
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                view.layoutParams = RecyclerView.LayoutParams(params)
                return view
            }
        }

    }
}
