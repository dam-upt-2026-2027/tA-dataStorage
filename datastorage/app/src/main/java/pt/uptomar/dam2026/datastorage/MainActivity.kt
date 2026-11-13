package pt.uptomar.dam2026.datastorage

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import pt.uptomar.dam2026.datastorage.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.Scanner

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

        // Task 2 - Shared Preferences with multiple files
        viewBinding.btWrite2.setOnClickListener {
            // write the text that user had written to shared Preferences multiple files
            writeSharedPreferencesMultiFiles()
            // hide the keyboard
            hideTheKeyboard(it)
        }

        viewBinding.btRead2.setOnClickListener {
            // read the shared Preferences data, from multiple files
            readSharedPreferencesMultiFiles()
        }

        // Task 3 - Cache
        viewBinding.btWrite3.setOnClickListener {
            // write the text that user had written to Cache
            writeCache()
            // hide the keyboard
            hideTheKeyboard(it)
        }

        viewBinding.btRead3.setOnClickListener {
            // read the cache data
            readCache()
        }


        // Task 4 - Internal Storage
        viewBinding.btWrite4.setOnClickListener {
            // write the text that user had written to Internal Storage
            writeInternalStorage()
            // hide the keyboard
            hideTheKeyboard(it)
        }

        viewBinding.btRead4.setOnClickListener {
            // read the Internal Storage data
            readInternalStorage()
        }


        // Task 5 - External Storage
        viewBinding.btWrite5.setOnClickListener {
            // write the text that user had written to External Storage
            writeEXternalStorage()
            // hide the keyboard
            hideTheKeyboard(it)
        }

        viewBinding.btRead5.setOnClickListener {
            // read the External Storage data
            readExternalStorage()
        }

    }


    /**
     * Read the data provided by user, from External Storage
     */
    private fun readExternalStorage() {
        val directory: File = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        val file = File(directory, "dadosExternalStorage.txt")
        try {
            val fi = FileInputStream(file)
            val sc = Scanner(fi)
            val nome = sc.nextLine()
            val idade = sc.nextLine()
            sc.close()
            fi.close()

            // mostrar os dados
            Snackbar.make(
                viewBinding.root,
                "Você chama-se ${
                    nome.toString().uppercase()
                } e tem $idade anos! - External Storage",
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, getString(R.string.external_storage_vazia), Toast.LENGTH_LONG)
                .show()
        }
    }


    /**
     * Write data on External Storage
     */
    private fun writeEXternalStorage() {
        /* if you are using Android lower than version 28
         * you MUST ask for permission to use it
         * you must add a annotation on the Manifest, and test if user give access to it
         */

        // endereço da pasta onde será gerado o ficheiro
        // associado à área de atuação da aplicação
        val directory: File = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!

        // nome do ficheiro na External Storage
        val file: File = File(directory, "dadosExternalStorage.txt")
        try {
            val fo: FileOutputStream = FileOutputStream(file)
            val ps: PrintStream = PrintStream(fo)
            ps.println(viewBinding.editText5a.text)
            ps.println(viewBinding.editText5b.text)
            ps.close()
            fo.close()

            // neste caso, vamos mostrar uma mensagem de 'conforto' ao utilizador
            Toast.makeText(this, getString(R.string.dados_guardados), Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.escritaErradaExternalStorage),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    /**
     * read data from the Internal Storage
     */
    private fun readInternalStorage() {
        val directory: File = filesDir
        val file = File(directory, "dadosInternalStorage.txt")
        try {
            val fi = FileInputStream(file)
            val sc = Scanner(fi)
            val nome = sc.nextLine()
            val idade = sc.nextLine()
            sc.close()
            fi.close()

            // mostrar os dados
            Snackbar.make(
                viewBinding.root,
                "Você chama-se ${
                    nome.toString().uppercase()
                } e tem $idade anos! - Internal Storage",
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, getString(R.string.internal_storage_vazia), Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * Write data on the Internal Storage
     */
    private fun writeInternalStorage() {
        // endereço da pasta onde será gerado o ficheiro
        // associado à área de atuação da aplicação
        val directory: File = filesDir
        // nome do ficheiro na Internal Storage
        val file: File = File(directory, "dadosInternalStorage.txt")
        try {
            val fo: FileOutputStream = FileOutputStream(file)
            val ps: PrintStream = PrintStream(fo)
            ps.println(viewBinding.editText4a.text)
            ps.println(viewBinding.editText4b.text)
            ps.close()
            fo.close()

            // neste caso, vamos mostrar uma mensagem de 'conforto' ao utilizador
            Toast.makeText(this, getString(R.string.dados_guardados), Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, getString(R.string.escritaErradaCache), Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * read data from Cache
     */
    private fun readCache() {
        val directory: File = cacheDir // <=> getCacheDir()
        val file = File(directory, "dadosCache.txt")
        try {
            val fi = FileInputStream(file)
            val sc = Scanner(fi)
            val nome = sc.nextLine()
            val idade = sc.nextLine()
            sc.close()
            fi.close()

            // mostrar os dados
            Snackbar.make(
                viewBinding.root,
                "Você chama-se ${nome.toString().uppercase()} e tem $idade anos!",
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, getString(R.string.cache_vazia), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Write data on Cache
     */
    private fun writeCache() {
        // endereço da pasta onde será gerado o ficheiro
        // associado à área de atuação da aplicação
        val directory: File = cacheDir
        // nome do ficheiro na Cache
        val file: File = File(directory, "dadosCache.txt")
        try {
            val fo: FileOutputStream = FileOutputStream(file)
            val ps: PrintStream = PrintStream(fo)
            ps.println(viewBinding.editText3a.text)
            ps.println(viewBinding.editText3b.text)
            ps.close()
            fo.close()

            // neste caso, vamos mostrar uma mensagem de 'conforto' ao utilizador
            Toast.makeText(this, getString(R.string.dados_guardados), Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Toast.makeText(this, getString(R.string.escritaErradaCache), Toast.LENGTH_LONG)
                .show()
        }

    }

    /**
     * read data from multiple files, using Shared Preferences
     */
    private fun readSharedPreferencesMultiFiles() {
        val sharedPreferencesNome = getSharedPreferences("nomes.dat", MODE_PRIVATE)
        val sharedPreferencesIdade = getSharedPreferences("idades.dat", MODE_PRIVATE)
        // ler o conteúdo da Shared Preferences. Se não for possível a leitura, devolve um texto alternativo
        // estou a usar o método getString() porque guardei 'texto'. Outros tipos de dados,
        // implicam outros métodos
        val nome = sharedPreferencesNome.getString("NOME", getString(R.string.txt_vazio))
        val idade = sharedPreferencesIdade.getInt("IDADE", 0)

        // usar os dados lidos
        Snackbar.make(
            viewBinding.root,
            "Você chama-se ${nome.toString().uppercase()} e tem $idade anos!",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    /**
     * write data on multiple files using Shared Preferences
     */
    private fun writeSharedPreferencesMultiFiles() {
        val sharedPreferencesNomes = getSharedPreferences("nomes.dat", MODE_PRIVATE)
        val sharedPreferencesIdades = getSharedPreferences("idades.dat", MODE_PRIVATE)

        val editorNomes: SharedPreferences.Editor = sharedPreferencesNomes.edit()
        val editorIdades: SharedPreferences.Editor = sharedPreferencesIdades.edit()

        // colocar o conteúdo que o utilizador escreveu nas Shared Preferences
        // Os dados são sempre escritos no formato: 'Variável'+'valor'
        // usa-se o método putXXXXX, onde XXXXX significa String, Int, Double, etc.
        editorNomes.putString("NOME", viewBinding.editText2a.text.toString())
        editorIdades.putInt(
            "IDADE",
            viewBinding.editText2b.text.toString().toIntOrNull() ?: 0
        ) // se a Idade=NULL, coloca 0
        // guardar as alterações
        editorNomes.commit()  // eventualmente, <=>   editorNomes.apply()
        editorIdades.commit()

        // neste caso, vamos mostrar uma mensagem de 'conforto' ao utilizador
        Toast.makeText(this, getString(R.string.dados_guardados), Toast.LENGTH_SHORT).show()
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