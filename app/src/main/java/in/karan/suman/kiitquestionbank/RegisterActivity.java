package in.karan.suman.kiitquestionbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText nameField,emailField,passwordField;
    Button register;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress= new ProgressDialog(this);
        nameField=findViewById(R.id.name);
        emailField=findViewById(R.id.email);
        passwordField=findViewById(R.id.password);
        register=findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

    }

    private void startRegister() {

        final String name,email,password;
        name=nameField.getText().toString().trim();
        email=emailField.getText().toString().trim();
        password=passwordField.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

            mProgress.setMessage("Signing Up ...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference current_user_db=mDatabase.child(user_id);

                        current_user_db.child("name").setValue(name);

                        mProgress.dismiss();

                        Intent mainIntent = new Intent(RegisterActivity.this,InsertBranch.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);


                    }


                }
            });
        }
    }
}
