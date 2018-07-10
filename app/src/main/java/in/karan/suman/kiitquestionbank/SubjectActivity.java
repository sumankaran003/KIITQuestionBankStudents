package in.karan.suman.kiitquestionbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectActivity extends AppCompatActivity {

    private RecyclerView mSubjectList;
    private DatabaseReference mDatabase;
    String branch,year;
    static ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);


        pd=new ProgressDialog(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KIIT Question Bank");



        mSubjectList= (RecyclerView) findViewById(R.id.subject_list);
        mSubjectList.setHasFixedSize(true);
        mSubjectList.setLayoutManager(new LinearLayoutManager(this));


        Intent box = getIntent();
        Bundle b = box.getExtras();
        branch=b.getString("branch");
        year=b.getString("year");
        mDatabase= FirebaseDatabase.getInstance().getReference().child(branch+"/"+year);


    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Subject,SubjectViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Subject, SubjectViewHolder>(

                Subject.class,
                R.layout.subject,
                SubjectViewHolder.class,
                mDatabase
        ) {



            @Override
            protected void populateViewHolder(SubjectViewHolder viewHolder, Subject model, final int position) {
               final String subject=getRef(position).getKey();
                viewHolder.setSubjectName(subject);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent question = new Intent(SubjectActivity.this,TypeActivity.class);
                        question.putExtra("branch" , branch);
                        question.putExtra("year" , year);
                        question.putExtra("subject" , subject);
                        startActivity(question);
                    }
                });

            }


        };

        mSubjectList.setAdapter(firebaseRecyclerAdapter);

    }



    public static class SubjectViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public SubjectViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setSubjectName(String title)
        {
            TextView post_title =  mView.findViewById(R.id.qname);
            post_title.setText(title);
        }



    }




}

