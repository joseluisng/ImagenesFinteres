package com.joseluisng.imagenesfinteres.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joseluisng.imagenesfinteres.ConstantesKt;
import com.joseluisng.imagenesfinteres.ExtensionFunctionsKt;
import com.joseluisng.imagenesfinteres.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddImagenActivityjava extends AppCompatActivity {

    ImageView image;
    Button busacarU, uploadImage;
    EditText titleImage, descriptionImage;
    int PICK_IMAGE_REQUEST = 111;
    String URL = ConstantesKt.getBaseUrl()+"/api/image";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen_activityjava);

        image = (ImageView) findViewById(R.id.imagenCargada);
        titleImage = (EditText) findViewById(R.id.etTitleI);
        descriptionImage = (EditText) findViewById(R.id.etDescriptionI);
        busacarU = (Button) findViewById(R.id.btnBuscarI);
        uploadImage = (Button) findViewById(R.id.btnGuardarI);

        busacarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleImage.getText().toString();
                String description = descriptionImage.getText().toString();

                if(bitmap != null && !title.isEmpty() && !description.isEmpty()){

                    subirImagen(bitmap, title, description);

                }else{
                    Toast.makeText(AddImagenActivityjava.this, "Proporcione una contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    void subirImagen(Bitmap bitmap, final String title, final String description){

        //converting image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                if(s.equals("true")){
                    Toast.makeText(AddImagenActivityjava.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddImagenActivityjava.this, "Some error occurred!", Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AddImagenActivityjava.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("image", imageString);
                parameters.put("title", title);
                parameters.put("description", description);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(AddImagenActivityjava.this);
        rQueue.add(request);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
