package in.karan.suman.kiitquestionbank;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class ViewQuestionActivity extends AppCompatActivity {

    String branch,year,qname,subject,type,url;
    private ImageView img;
    private int currentpage = 0;
    private Button next, previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("KIIT Question Bank");

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);


        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                currentpage++;
                render();
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                currentpage--;
                render();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void render() {

        try {

            img = (ImageView) findViewById(R.id.image);

            int width = img.getWidth();
            int height = img.getHeight();

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

            File file = new File("/sdcard/Example.pdf");

            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));

            if(currentpage<0){
                currentpage =0;

            }else if(currentpage > renderer.getPageCount()){


                currentpage = renderer.getPageCount() -1;

                Matrix matrix = img.getImageMatrix();

                Rect rect = new Rect(0,0, width , height);

                renderer.openPage(currentpage).render(bitmap,rect,matrix , PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY );

                img.setImageMatrix(matrix);
                img.setImageBitmap(bitmap);
                img.invalidate();

            }


        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
