package in.karan.suman.kiitquestionbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity
{

    Spinner s1,s2,s4,s3,s5;
    final int REQUEST_CODE = 6;
    String branch[] = {"Computer Science","Computer Application","Civil","Computer Science","Electrical","Electronics","Mechanical",
            "Humanities and Social Sciences","Applied Sciences","Management","Biotechnology","Rural Management",
            "Law","Fashion Technology","Film and Media Sciences","Medicine"};
    String year[] = {"1st","2nd","3rd","4th","5th","6th"};
    // String type[] = {"All","Mid Sem","End Sem","supplementary"};
    // String degree[] = {"B.Tech","M.Tech","P.hd"};

    String spinner1,spinner2;
    Button btn1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KIIT Question Bank");




        s1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,branch);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinner1 =branch[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        s2=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,year);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinner2 =year[position];
                //Toast.makeText(MainActivity.this,"you selected : "+str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        btn1= (Button) findViewById(R.id.button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SubjectActivity.class);
                intent.putExtra("branch" , spinner1);
                intent.putExtra("year" , spinner2);
                startActivityForResult(intent , REQUEST_CODE);
                startActivity(intent);
            }
        });







    }
}

