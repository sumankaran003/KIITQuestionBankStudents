package in.karan.suman.kiitquestionbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    ImageButton student,teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KIIT Question Bank");
        student= (ImageButton) findViewById(R.id.imageButton);
        teacher= (ImageButton) findViewById(R.id.imageButton1);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(student);
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent teacher = new Intent(Main2Activity.this,InsertBranch.class);
                startActivity(teacher);
            }
        });
    }
}
