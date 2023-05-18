package com.example.khedma.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.khedma.R
import com.example.khedma.viewmodel.repository.UserRepository
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// keytool -exportcert -alias androiddebugkey -keystore "C:\Users\USERNAME\.android\debug.keystore" | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" sha1 -binary | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" base64


class Singin_up_Activity : AppCompatActivity() {
    private var isChecked: Boolean = false
    lateinit var animationNoreponse: LottieAnimationView
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singin_up)
        val emailloginText = findViewById<EditText>(R.id.eMail)
        val passwordloginText = findViewById<EditText>(R.id.passwords)
        val emailsingupText = findViewById<EditText>(R.id.eMails)
        val passwordsingupText = findViewById<EditText>(R.id.passwordss)//ForgetPassword
        val ForgetPassword = findViewById<TextView>(R.id.ForgetPassword)//ForgetPassword
        val myCheckBox = findViewById<CheckBox>(R.id.my_checkbox)
        val confirmPasswordsingupText = findViewById<EditText>(R.id.passwords01)
        val facebook = findViewById<ImageView>(R.id.facebook)
        val userRepository = UserRepository()

        animationNoreponse = findViewById(R.id.animationNoreponse)
        animationNoreponse.playAnimation()
        animationNoreponse.loop(true)
        // Reference views by their IDs
        val singUp = findViewById<TextView>(R.id.singUp)
        val logIn = findViewById<TextView>(R.id.logIn)
        val singUpLayout = findViewById<View>(R.id.singUpLayout)
        val logInLayout = findViewById<View>(R.id.logInLayout)
        val singup = findViewById<TextView>(R.id.singup)
        val login = findViewById<TextView>(R.id.login)
        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        // Set onClickListeners for buttons
        myCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            // Your code here
            this.isChecked = isChecked
        }
        if (isLoggedAlread()) {
            val intent = Intent(this@Singin_up_Activity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        ForgetPassword.setOnClickListener {
            val intent = Intent(this@Singin_up_Activity, Forgetpassword::class.java)
            startActivity(intent)
            finish()
        }
        singUp.setOnClickListener {
            singUp.background = resources.getDrawable(R.drawable.switch_trcks,null)
            singUp.setTextColor(resources.getColor(R.color.textColor,null))
            logIn.background = null
            singUpLayout.visibility = View.VISIBLE
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor,null))
        }

        logIn.setOnClickListener {
            singUp.background = null
            singUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            logIn.background = resources.getDrawable(R.drawable.switch_trcks,null)
            singUpLayout.visibility = View.GONE
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.textColor,null))
        }

        singup.setOnClickListener {
            val email = emailsingupText.text.toString().trim()
            val password = passwordsingupText.text.toString().trim()
            val confirmPassword = confirmPasswordsingupText.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                // Show an alert dialog if either field is empty
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter both email and password")
                    .setPositiveButton("OK", null)
                    .show()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailsingupText.error = "Please enter a valid email address"
                emailsingupText.requestFocus()
            return@setOnClickListener
            }

            // Check if password and confirm password match
            if (password != confirmPassword) {
                confirmPasswordsingupText.error = "Passwords do not match"
                confirmPasswordsingupText.requestFocus()
                return@setOnClickListener
            }

            // Check if password is at least 8 characters long
            if (password.length < 8) {
                passwordsingupText.error = "Password must be at least 8 characters long"
                passwordsingupText.requestFocus()
                return@setOnClickListener
            }
            // Perform singup with valid email and password
            //HERE

            CoroutineScope(Dispatchers.Main).launch {
                val success = userRepository.registerUser(this@Singin_up_Activity , email, password)
                if (success) {
                    // Registration successful, navigate to another page
                   val intent = Intent(this@Singin_up_Activity, Singin_up_Activity::class.java)
                    startActivity(intent)
                    finish()
                }

            }

        }

        login.setOnClickListener {
            val emaill = emailloginText.text.toString().trim()
            val passwordd = passwordloginText.text.toString().trim()
            if (emaill.isEmpty() || passwordd.isEmpty()) {
                // Show an alert dialog if either field is empty
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter both email and password")
                    .setPositiveButton("OK", null)
                    .show()
            }
            CoroutineScope(Dispatchers.Main).launch {
                val success = userRepository.loginUser(this@Singin_up_Activity , emaill, passwordd)
                if (success && isChecked) {
                    // Change the variable loggedIn to true if the checkbox is checked
                    var loggedIn = true
                    editor.putBoolean("loggedIn", true)
                }
                else if (success) {
                    // Registration successful, navigate to another page
                       val intent = Intent(this@Singin_up_Activity, SlideActivity::class.java)
                       startActivity(intent)
                       finish()
                }else if (!success) {
                Handler(Looper.getMainLooper()).post {
                    android.app.AlertDialog.Builder(this@Singin_up_Activity)
                        .setTitle("Error")
                        .setMessage("email/password incorrect")
                        .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
                        .show()
                }}
            }
        }


    }

    private fun isLoggedAlread(): Boolean {
        val sharedPreferences =
            getSharedPreferences("loggedIn", MODE_PRIVATE)
        return sharedPreferences.getBoolean("loggedIn", false)
    }
}
