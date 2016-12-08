# CountryPicker

Android Library to display a fragment dialog to choose a country. When the user
chooses a country, you get the name of the selected country and its dial code.

## Usage

First, implement `CountryPickerListener` in your activity. The `onSelectCountry` 
is called once the use chooses a country.

```
class MainActivity : AppCompatActivity(), CountryPickerListener {
    var fab : FloatingActionButton? = null

    override fun onSelectCountry(code: String, dial_code: String, name: String) {
        Snackbar.make(fab!!, "Selected $name ($dial_code)", Snackbar.LENGTH_SHORT).show()
    }

//..
}
```

Then, create a new instance of `CountryPicker` and show it via `SupportFragmentManager`.

```
    override fun onCreate(savedInstanceState: Bundle?) {
        //...

        fab = findViewById(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener({ view ->
            val countryPicker = CountryPicker.newInstance("Select your Country")
            countryPicker.show(supportFragmentManager, "Dialog")
             })
    }
```








