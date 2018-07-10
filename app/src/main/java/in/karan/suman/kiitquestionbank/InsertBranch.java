package in.karan.suman.kiitquestionbank;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class InsertBranch extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5,et6;
    final static int PICK_PDF_CODE = 2342;
    Button upload;
    private Uri mImageUri;
    ProgressBar progressBar;
    TextView textViewStatus;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    private static final int GALLERY_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {

                    Intent loginIntent = new Intent(InsertBranch.this,LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }
            }
        };

        mStorageReference = FirebaseStorage.getInstance().getReference();
       // mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        setContentView(R.layout.activity_insert_branch);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et5 = (EditText) findViewById(R.id.editText5);
        upload= (Button) findViewById(R.id.upload);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(InsertBranch.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:" + getPackageName()));


                    startActivity(intent);
                    return;
                }

                //creating an intent for file chooser
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);


        final String branch = et1.getText().toString();
        final String year = et2.getText().toString();
        final String subject = et3.getText().toString();
        final String type = et4.getText().toString();
        final String qyear = et5.getText().toString();
        final String qname = branch+" "+year+" year "+subject+" "+type+" "+qyear;

        final String str=branch+"/"+year+"/"+subject+"/"+type+"/"+qname;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();



        StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File Uploaded Successfully");

                        Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());
                        DatabaseReference mDatabaseReference = database.getReference(branch+"/"+year+"/"+subject+"/"+type+"/"+qname);
                        mDatabaseReference.child("url").setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });

    }



  /*  public  void buttonClick(View v)
    {
        String branch = et1.getText().toString();
        String year = et2.getText().toString();
        String subject = et3.getText().toString();
        String type = et4.getText().toString();
        String qyear = et5.getText().toString();
        String qname = branch+" "+year+" year "+subject+" "+type+" "+qyear;


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef1 = database.getReference(branch+"/"+year+"/"+subject+"/"+type+"/"+qname);
        myRef1.child("url").setValue(" ");


        if(!TextUtils.isEmpty(branch) && !TextUtils.isEmpty(year) && !TextUtils.isEmpty(subject) && !TextUtils.isEmpty(type) &&
                !TextUtils.isEmpty(qyear) && !TextUtils.isEmpty(qname) && mImageUri !=null){



        }



    }*/

  public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.insertbranch_menu,menu);
      return super.onCreateOptionsMenu(menu);
  }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_logout){
            logout();

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }
}
