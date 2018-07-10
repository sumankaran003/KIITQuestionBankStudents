package in.karan.suman.kiitquestionbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TypeActivity extends AppCompatActivity {

    Spinner s1,s2,s4,s3;String branch,year,qname,subject,type;


    private RecyclerView mTypeList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KIIT Question Bank");

        Intent box = getIntent();
        Bundle b = box.getExtras();
        branch=b.getString("branch");
        year=b.getString("year");
        subject=b.getString("subject");

        mTypeList= (RecyclerView) findViewById(R.id.type_list);
        mTypeList.setHasFixedSize(true);
        mTypeList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase= FirebaseDatabase.getInstance().getReference().child(branch+"/"+year+"/"+subject);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Subject,TypeActivity.TypeViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Subject, TypeActivity.TypeViewHolder>(

                Subject.class,
                R.layout.subject,
                TypeActivity.TypeViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(TypeActivity.TypeViewHolder viewHolder, Subject model, final int position) {
                final String types=getRef(position).getKey();
                viewHolder.setTypeName(types);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent type = new Intent(TypeActivity.this,QuestionActivity.class);
                        type.putExtra("branch" , branch);
                        type.putExtra("year" , year);
                        type.putExtra("subject" , subject);
                        type.putExtra("type" , types);
                        startActivity(type);
                    }
                });

            }
        };

        mTypeList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TypeViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public TypeViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTypeName(String title)
        {
            TextView post_title =  mView.findViewById(R.id.qname);
            post_title.setText(title);
        }

    }

}
