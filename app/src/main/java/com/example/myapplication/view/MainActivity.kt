package com.example.myapplication.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.R
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.viewModel.repository.UserRepository
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
// keytool -exportcert -alias androiddebugkey -keystore "C:\Users\USERNAME\.android\debug.keystore" | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" sha1 -binary | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" base64


class MainActivity : AppCompatActivity() {
    lateinit var animationNoreponse: LottieAnimationView
    private lateinit var callbackManager: CallbackManager
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val emailloginText = findViewById<EditText>(R.id.eMail)
        val passwordloginText = findViewById<EditText>(R.id.passwords)
        val emailsingupText = findViewById<EditText>(R.id.eMails)
        val passwordsingupText = findViewById<EditText>(R.id.passwordss)
        val confirmPasswordsingupText = findViewById<EditText>(R.id.passwords01)
        val facebook = findViewById<ImageView>(R.id.facebook)
        callbackManager=CallbackManager.Factory.create()
        val accessToken= AccessToken.getCurrentAccessToken()
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

        // Set onClickListeners for buttons

        if (accessToken!=null && !accessToken.isExpired){

            startActivity(Intent(this,profileActivity::class.java))

            finish()

        }
        LoginManager.getInstance().registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {
            }

            override fun onSuccess(result: LoginResult) {
                startActivity(Intent(this@MainActivity,profileActivity::class.java))

                finish()
            }

        } )
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
                val success = userRepository.registerUser(this@MainActivity , email, password)
                if (success) {
                    // Registration successful, navigate to another page
                   val intent = Intent(this@MainActivity, profileActivity::class.java)
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
                val success = userRepository.loginUser(this@MainActivity , emaill, passwordd)
                if (success) {
                    // Registration successful, navigate to another page
                       val intent = Intent(this@MainActivity, profileActivity::class.java)
                       startActivity(intent)
                       finish()
                }else if (!success) {
                Handler(Looper.getMainLooper()).post {
                    android.app.AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error")
                        .setMessage("email/password incorrect")
                        .setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
                        .show()
                }}
            }
        }
        facebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile,email"))


        }
    }
}
