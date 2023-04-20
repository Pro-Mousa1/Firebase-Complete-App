package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var editname:EditText
    lateinit var editemail:EditText
    lateinit var editIdNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editname = findViewById(R.id.mEditName)
        editemail = findViewById(R.id.mEditEmail)
        editIdNumber = findViewById(R.id.mEditIdNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        btnSave.setOnClickListener {
            // Receive data from the user
            var name = editname.text.toString().trim()
            var email = editemail.text.toString().trim()
            var idNumber = editIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            // Check if the user is submitting empty fields
            if (name.isEmpty()){
                editname.setError("Please fill this field")
                editname.requestFocus()
            }else if (email.isEmpty()){
                editemail.setError("Please fill this field")
                editemail.requestFocus()
            }else if (idNumber.isEmpty()){
                editIdNumber.setError("Please fill this field")
                editIdNumber.requestFocus()
            }else{
                // Proceed to save data
                var user = User(name, email, idNumber, id)
                // Create a reference to FirebaseDatabase
                var ref = FirebaseDatabase.getInstance().
                getReference().child("Users/"+id)
                //Start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "User saved successfully",
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "User saving failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        btnView.setOnClickListener {
            var intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }
    }
}