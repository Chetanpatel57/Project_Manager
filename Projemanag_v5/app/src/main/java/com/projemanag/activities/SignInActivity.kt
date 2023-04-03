package com.projemanag.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.projemanag.R
import com.projemanag.model.User
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_sign_in)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        // START
        btn_sign_in.setOnClickListener {
            signInRegisteredUser()
        }
        // END
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_sign_in_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun signInRegisteredUser() {
        // Here we get the text from editText and trim the space
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        Toast.makeText(
                            this@SignInActivity,
                            "You have successfully signed in.",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
    // END

    // START
    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }
    // END

    // START
    fun signInSuccess(user: User) {

        hideProgressDialog()

        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
    }
    // END
}