package in.karan.suman.kiitquestionbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionActivity extends AppCompatActivity
{

    ProgressBar progressBar;
    Spinner s1,s2,s4,s3;String branch,year,qname,subject,type;
    String Anss[] = {"All","Answer Available","Answer Not Available"};
    String years[] = {"All","Prev Year","Prev 2 Year","Prev 5 year"};
    String types[] = {"All","Mid Sem","End Sem","supplementary"};

    private RecyclerView mQuestionList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KIIT Question Bank");


        Intent box = getIntent();
        Bundle b = box.getExtras();
        branch=b.getString("branch");
        year=b.getString("year");
        subject=b.getString("subject");
        type=b.getString("type");


        mQuestionList= (RecyclerView) findViewById(R.id.question_list);
        //mQuestionList.setHasFixedSize(true);
        mQuestionList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase= FirebaseDatabase.getInstance().getReference().child(branch+"/"+year+"/"+subject+"/"+type);

    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<Subject,QuestionActivity.QuestionViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Subject, QuestionActivity.QuestionViewHolder>(

                Subject.class,
                R.layout.subject,
                QuestionActivity.QuestionViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(QuestionActivity.QuestionViewHolder viewHolder, Subject model, final int position) {
                final String qname=getRef(position).getKey();


                viewHolder.setQuestionName(qname);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference(branch+"/"+year+"/"+subject+"/"+type+"/"+qname+"/url");
                        DatabaseReference myRef1=myRef.child("url");



                      /*  DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference mostafa = ref.child("Users").child("mostafa_farahat22@yahoo.com").child("_email");*/

                        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String url = dataSnapshot.getValue(String.class);
                             //  Toast.makeText(QuestionActivity.this,url,Toast.LENGTH_LONG).show();
                               // Intent intent = new Intent(Intent.ACTION_VIEW);
                               // intent.putExtra("url" , url);
                              //  intent.setData(Uri.parse(url));
                              //  startActivity(intent);

                                Intent question = new Intent(QuestionActivity.this,PDFViewActivity.class);
                                question.putExtra("url" , url);
                               // question.putExtra("year" , year);
                               // question.putExtra("subject" , subject);
                                startActivity(question);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                      //  String url=myRef.get("url").toString();
                     /*   Intent question = new Intent(QuestionActivity.this,ViewQuestionActivity.class);
                        question.putExtra("branch" , branch);
                        question.putExtra("year" , year);
                        question.putExtra("subject" , subject);
                        question.putExtra("type" , type);
                        question.putExtra("qname" , qname);
                        question.putExtra("url" , url[0]);
                        startActivity(question);
                        question.setData(Uri.parse(url[0]));
                        startActivity(question);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url[0]));
                        startActivity(intent);*/
                    }
                });

            }
        };

        mQuestionList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setQuestionName(String title)
        {
            TextView post_title =  mView.findViewById(R.id.qname);
            post_title.setText(title);
        }

    }


}
