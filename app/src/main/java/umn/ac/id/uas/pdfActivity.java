package umn.ac.id.uas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class pdfActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri filepath;
    Map<String, Object> mp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent terima = getIntent();
        Map<String, Object> mp = (HashMap<String, Object>)terima.getExtras().get("reports");
        filepath = (Uri)terima.getExtras().get("imagepath");
        final Button PrintButton = (Button)findViewById(R.id.ButtonPrint);
        PrintButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PrintButton.setVisibility(View.GONE);
                printPDF();
                PrintButton.setVisibility(View.VISIBLE);
            }
        });

        final String email_user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ImageView gambar = (ImageView) findViewById(R.id.imagereport);
        String namaGambar = mp.get("gambar").toString();
        String urlGambar  = "gs://k3l-umn.appspot.com/All_Image_Uploads/"+namaGambar;
        StorageReference imgRef = FirebaseStorage.getInstance().getReferenceFromUrl(urlGambar);
        Glide.with(pdfActivity.this)
                .load(imgRef)
                .into(gambar);

        TextView deskripsi = (TextView) findViewById(R.id.descHalaman);
        String desc = mp.get("description").toString();
        deskripsi.setText(desc);

        TextView lokasi = (TextView) findViewById(R.id.locationHalaman);
        String locations = mp.get("location").toString();
        lokasi.setText(locations);

        TextView phonenumber = (TextView) findViewById(R.id.phonenumberHalaman);
        String phoneNumber = mp.get("phoneNum").toString();
        phonenumber.setText(phoneNumber);

        TextView emails = (TextView) findViewById(R.id.emailHalaman);
        emails.setText(email_user);
    }
    public void onStart() {
        super.onStart();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.titlepdf, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:

                finish();

                return true;
            case R.id.action_logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
    private void getList(String judul){
        db.collection("reports").document(judul)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        mp = documentSnapshot.getData();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public boolean printPDF(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat rt = new SimpleDateFormat("d-MMM-yyyy-HH-mm-ss");
        final String formattedDaterp = rt.format(c);
        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        File fol = new File(extstoragedir, "report");
        File folder=new File(fol,"pdf");
        if(!folder.exists()) {
            boolean bool = folder.mkdirs();
            Toast.makeText(getApplicationContext(),"making directories",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(),extstoragedir,Toast.LENGTH_SHORT).show();
        try {
            final File file = new File(folder, "report"+ formattedDaterp +".pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfDocument document = new PdfDocument();
            DisplayMetrics displaymetrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            RelativeLayout content = (RelativeLayout)findViewById(R.id.mainContent);

            PdfDocument.PageInfo pageInfo =new PdfDocument.PageInfo.Builder(width, height,1).create();

            PdfDocument.Page page = document.startPage(pageInfo);

            content.draw(page.getCanvas());
            document.finishPage(page);
            PdfDocument.PageInfo pageInfo2 =new PdfDocument.PageInfo.Builder(width, height,2).create();
            PdfDocument.Page page2 = document.startPage(pageInfo2);

            findViewById(R.id.contentpdf).setVisibility(View.GONE);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            ImageView myImage = (ImageView) findViewById(R.id.imagereport);
            myImage.setVisibility(View.VISIBLE);
            myImage.setImageBitmap(bitmap);
            Paint paint = null;
            Canvas canvas2 = page2.getCanvas();
            canvas2.drawBitmap(bitmap, 0, 0, paint);
            document.finishPage(page2);
            document.writeTo(fOut);
            document.close();
            myImage.setVisibility(View.GONE);
            findViewById(R.id.contentpdf).setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Create file success, width " + content.getWidth() + " height" + content.getHeight(),Toast.LENGTH_SHORT).show();
            return true;
        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"Create file error",Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
