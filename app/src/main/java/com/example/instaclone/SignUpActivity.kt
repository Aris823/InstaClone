package com.example.instaclone

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Button
import com.example.instaclone.databinding.ActivitySignInBinding
import com.example.instaclone.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private val view : ActivitySignUpBinding by lazy { ActivitySignUpBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.signinLinkBtn.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
        }

        view.signupBtn.setOnClickListener{
            CreateAccount()
        }
    }

    private fun CreateAccount()
    {
        val fullName = view.fullnameSignup.text.toString()
        val userName = view.usernameSignup.text.toString()
        val email = view.emailSignup.text.toString()
        val password = view.passwordSignup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this, "full name is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this, "user name is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "email is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "password is required.", Toast.LENGTH_LONG).show()

            else -> {
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait, this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful)
                        {
                            saveUserInfo(fullName, userName, email, progressDialog)
                        }
                        else
                        {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private  fun saveUserInfo(fullName: String, userName: String, email:String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("User")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = currentUserID
        userMap["email"] = currentUserID
        userMap["email"] = currentUserID
        userMap["image"] =
            "https://firebasestorage.googleapis.com/v0/b/instagram-clone-27d25.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=f7f661a7-8ca9-448e-a48e-8bfde4b4b1ad"

        userRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        "Account has been created successfully.",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    val message = task.exception!!.toString()
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }

            }


    }
}