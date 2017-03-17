package criptext.com.sampleapp

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import criptext.com.countrypicker.CountryPicker
import criptext.com.countrypicker.CountryPickerListener

class MainActivity : AppCompatActivity(), CountryPickerListener {
    var fab : FloatingActionButton? = null

    override fun onSelectCountry(code: String, dial_code: String, name: String) {
        Snackbar.make(fab!!, "Selected $name ($dial_code)", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)


        fab = findViewById(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener({ view ->
            val countryPicker = CountryPicker.newInstance("Select your Country")
            countryPicker.show(supportFragmentManager, "Dialog")
             })
    }

}
