package pt.uptomar.dam2026.datastorage

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import pt.uptomar.dam2026.datastorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // instantiate the viewBinding variable
        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Task 1 - Shared Preferences
        viewBinding.btWrite1.setOnClickListener {
            // write the text that user had written to shared Preferences
            writeSharedPreferences()
            // hide the keyboard
            hideTheKeyboard(it)
        }

        viewBinding.btRead1.setOnClickListener {
            // read the shared Preferences data
            readSharedPreferences()
        }

    }

    /**
     * hides the keyboard when user stops writing
     */
    fun hideTheKeyboard(view: View) {
        // hide the keyboard
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * write to the Shared Preferences the value that user har written on text box
     */
    fun writeSharedPreferences() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Place the content that the user wrote in the Shared Preferences
        // Data is always written in the format: 'Variable' + 'value'
        // it use the method putXXXXX, where XXXXX means String, Int, Double, etc.
        editor.putString("SPreferenceSimples", viewBinding.editText1.text.toString())
        // store changes
        editor.commit()  // you can use also, <=>   editor.apply()

        // in this case, we are going to show a 'confort' message to the user
        Toast.makeText(this, getString(R.string.dados_guardados), Toast.LENGTH_SHORT).show()

    }

    /**
     * retrives what was stored on Shared Preferences
     */
    fun readSharedPreferences() {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        // read the Shared Preferences data.
        // If is not possible to read, it resolves an alternative text
        // It is used the method getString() because a sting was stored
        // For other types of data, is necessary to reference the correspondent methods,
        val texto = sharedPreferences.getString("SPreferenceSimples", "")

        // use data that was recovered
        //Snackbar.make(viewBinding.root, "Introduziu: $texto", Snackbar.LENGTH_LONG).show()
        Snackbar.make(
            viewBinding.root,
            getString(R.string.textOne, texto),
            Snackbar.LENGTH_LONG
        ).show()

    }


}