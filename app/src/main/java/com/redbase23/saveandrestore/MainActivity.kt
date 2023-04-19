package com.redbase23.saveandrestore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.redbase23.saveandrestore.databinding.ActivityMainBinding
import java.util.Locale
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.discountButton.setOnClickListener {
            val firstName = binding.firstName.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()
            val email = binding.email.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
                Toast.makeText(this,getString(R.string.add_text_validation),Toast.LENGTH_SHORT).show()
            } else {
                val fullName = firstName.plus(" ").plus(lastName)

                binding.discountCodeConfirmation.text = getString(R.string.discount_code_confirmation,fullName)
                binding.discountCode.text = UUID.randomUUID().toString().take(8)
                    .uppercase(Locale.getDefault())

                hideKeyboard()
                clearInputFields()

            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.discountCode.text = savedInstanceState.getString(DISCOUNT_CODE,"")
        binding.discountCodeConfirmation.text = savedInstanceState.getString(
            DISCOUNT_CONFIRMATION_MESSAGE,"")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(DISCOUNT_CODE,binding.discountCode.text.toString())
        outState.putString(DISCOUNT_CONFIRMATION_MESSAGE,binding.discountCodeConfirmation.text.toString())
    }

    private fun clearInputFields() {
        binding.firstName.text.clear()
        binding.lastName.text.clear()
        binding.email.text.clear()
    }

    private fun hideKeyboard() {
        if (currentFocus != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }

    companion object{
        private const val TAG ="MainActivity"
        private const val DISCOUNT_CONFIRMATION_MESSAGE = "DISCOUNT_CONFIRMATION_MESSAGE"
        private const val DISCOUNT_CODE = "DISCOUNT_CODE"
    }
}