# CountryPicker

Android Library to display a fragment dialog to choose a country. When the user
chooses a country, you get the name of the selected country and its dial code.

## Install

add this line to your `build.gradle`:

```gradle
compile 'com.github.Criptext:CountryPicker:1.0'
```

## Usage

First, implement `CountryPickerListener` in your activity. The `onSelectCountry` 
is called once the use chooses a country.

```kotlin
class MainActivity : AppCompatActivity(), CountryPickerListener {
    var fab : FloatingActionButton? = null

    override fun onSelectCountry(code: String, dial_code: String, name: String) 
    {
        val message = "Selected $name ($dial_code)"
        Snackbar.make(fab!!, message, Snackbar.LENGTH_SHORT)
            .show()
    }

//..
}
```

Then, create a new instance of `CountryPicker` and show it via 
`SupportFragmentManager`.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) { //...

        fab = findViewById(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener({ view ->
            val countryPicker = CountryPicker.newInstance("Select your Country")
            countryPicker.show(supportFragmentManager, "Dialog")
             })
    }
```
