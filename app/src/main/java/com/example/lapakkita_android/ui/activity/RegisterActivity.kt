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
import com.example.lapakkita_android.databinding.ActivityRegisterBinding
import com.example.lapakkita_android.ui.components.BackButton
import com.example.lapakkita_android.ui.components.RegisterForm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:  FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.registerForm.setContent {
            RegisterForm(
                googleSignIn = { googleSignIn() },
                signUp = { email, password ->
                    signUp(email, password)
                }
            )
        }
        binding.backBtn.setContent {
            BackButton(
                onClick = {finish()}
            )
        }

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signUp(
        email: String,
        password: String
    ) {
        binding.progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful){
                    startActivity(
                        Intent(
                            this,
                            OnboardActivity::class.java
                        ).addFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        )
                    )
                }
                else{
                    Log.d(TAG, task.exception.toString())
                    val message = if(task.exception.toString().contains(
                            "email address is already in use ",
                            ignoreCase = true
                        )
                    ) {
                        resources.getString(R.string.used_email)
                    }
                    else{
                        resources.getString(R.string.connection_error)
                    }
                    Toast.makeText(
                        this,
                        "${resources.getString(R.string.sign_up_failed)}\n$message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun googleSignIn() {
        binding.progressBar.visibility = View.VISIBLE
        val googleSignInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(googleSignInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Log.d(LoginActivity.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this, resources.getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                    Log.w(LoginActivity.TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(LoginActivity.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(LoginActivity.TAG, "Google sign in failed", e)
            }
        }
    }

    companion object{
        const val TAG = "LapakKita_Register"
    }
}