package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserupdateActivity : AppCompatActivity() {
    lateinit var editname: EditText
    lateinit var editemail: EditText
    lateinit var editIdNumber: EditText
    lateinit var btnUpdate: Button
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdate)
        editname = findViewById(R.id.mEditName)
        editemail = findViewById(R.id.mEditEmail)
        editIdNumber = findViewById(R.id.mEditIdNumber)
        btnUpdate = findViewById(R.id.mBtnUpdate)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Updating")
        progressDialog.setMessage("Please wait...")
        // Receive data from the intent
        var receivedName = intent.getStringExtra("name")
        var receivedEmail = intent.getStringExtra("email")
        var receivedIdNumber = intent.getStringExtra("idNumber")
        var receivedid = intent.getStringExtra("id")

        // Display the received date on the EditTexts
        editname.setText(receivedName)
        editemail.setText(receivedEmail)
        editIdNumber.setText(receivedIdNumber)

        // Set an OnClick listener to button update
        btnUpdate.setOnClickListener {
            // Receive data from the user
            var name = editname.text.toString().trim()
            var email = editemail.text.toString().trim()
            var idNumber = editIdNumber.text.toString().trim()
            var id = receivedid!!
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
                //Start updating
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "User updated successfully",
                            Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UserupdateActivity,
                            UsersActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "User updating failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}