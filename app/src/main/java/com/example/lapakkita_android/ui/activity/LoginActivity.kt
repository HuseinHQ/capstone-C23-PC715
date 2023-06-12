package com.example.lapakkita_android.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityLoginBinding
import com.example.lapakkita_android.ui.components.BackButton
import com.example.lapakkita_android.ui.components.LoginForm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupComponent()

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
    }

    private fun setupComponent() {
        binding.loginForm.setContent {
            LoginForm(
                signIn = { email, pass ->
                     signIn(email, pass)
                },
                googleSignIn = { googleSignIn()}
            )
        }

        binding.backBtn.setContent {
            BackButton(
                onClick = {finish()}
            )
        }
    }

    private fun signIn(
        email: String,
        pass: String
    ) {
        binding.progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful){
                    val user = auth.currentUser
                    updateUI(user)
                }
                else{
                    Log.d(TAG, task.exception.toString())
                    val message = if(task.exception.toString().contains(
                            "password is invalid",
                            ignoreCase = true
                        ) || task.exception.toString().contains(
                            "no user record",
                            ignoreCase = true
                        )
                    ) {
                        resources.getString(R.string.incorrect_email_password)
                    }
                    else{
                        resources.getString(R.string.connection_error)
                    }
                    Toast.makeText(
                        this,
                        "${resources.getString(R.string.login_failed)}\n$message",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun googleSignIn() {
        val googleSignInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(googleSignInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, resources.getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }

    companion object{
        var PHOTO_PROFILE_SET = "photo_profile_set"
        const val TAG = "LapakKita_Login"
    }
}