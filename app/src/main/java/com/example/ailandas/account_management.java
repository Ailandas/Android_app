package com.example.ailandas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.File;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static com.example.ailandas.R.layout.activity_account_managment2;

public class account_management extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int PICK_IMAGE = 100;
    BottomNavigationView bottomNavigationView;
    LinearLayout loggedOut;
    LinearLayout loggedIn;
    LinearLayout userData;
    Button Register;
    FileHelper fh;
    Button Login;
    TextView LoggedInUsername;
    TextView LoggedInEmail;
    Button LogOut;
    ImageView personImage;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_account_managment2);
        getSupportActionBar().hide();
        fh=new FileHelper();
        Context context=this;
        userData = (LinearLayout) findViewById(R.id.userDataLayout);
        LogOut=(Button) findViewById(R.id.buttonLogOut);
        loggedOut = (LinearLayout) findViewById(R.id.LoggedOut);
        loggedIn = (LinearLayout) findViewById(R.id.LoggedIn);
        LoggedInUsername=(TextView) findViewById(R.id.userDataUsername) ;
        LoggedInEmail=(TextView) findViewById(R.id.userDataEmail);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.botomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        Register = (Button) findViewById(R.id.buttonNoAccount);
        Login = (Button) findViewById(R.id.loginButton);
        personImage=(ImageView) findViewById(R.id.personImage) ;
        personImage.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                openGallery();
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SaveSharedPreference.setPrefUserEmail(context,"");
                SaveSharedPreference.setUserName(context,"");
                SaveSharedPreference.setPrefUserImageUri(context,"");
                userData.setVisibility(View.INVISIBLE);
                loggedOut.setVisibility(View.VISIBLE);


            }
        });
        if(SaveSharedPreference.getPrefActivityId(this)!=0) {
            bottomNavigationView.getMenu().findItem(SaveSharedPreference.getPrefActivityId(this)).setChecked(true);
        }
        if(SaveSharedPreference.getUserName(context).length()>0) {

            userData.setVisibility(View.VISIBLE);
            loggedOut.setVisibility(View.INVISIBLE);
            loggedIn.setVisibility(View.INVISIBLE);

            if(SaveSharedPreference.getPrefUserImageUri(this).length()>15) {
                Uri image = Uri.parse(SaveSharedPreference.getPrefUserImageUri(this));
                personImage.setImageURI(image);
            }
            LoggedInUsername.setText(SaveSharedPreference.getUserName(context));
            LoggedInEmail.setText(SaveSharedPreference.getPrefUserEmail(context));
        }
        else{
            userData.setVisibility(View.INVISIBLE);
            loggedOut.setVisibility(View.VISIBLE);
        }

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editEmail = (EditText) findViewById(R.id.loginEmail);
                    String loginEmail = (String) editEmail.getText().toString();

                    EditText editPassword = (EditText) findViewById(R.id.loginPass);
                    String loginPassword = (String) editPassword.getText().toString();

                    String usersData = fh.ReadBtn(context, "users.txt");

                    String[] content = usersData.split("\\r?\\n");
                    String newData = "";
                    for (int i = 0; i < content.length; i++) {
                        String[] singleUserData = content[i].split(",");
                        if (singleUserData[2].equals(loginEmail) && singleUserData[1].equals(loginPassword)) {
                            SaveSharedPreference.setUserName(context, singleUserData[0]);
                           SaveSharedPreference.setPrefUserEmail(context,singleUserData[2]);
                            Toast.makeText(context, "Sėkmingai prisijungta!",
                                    Toast.LENGTH_SHORT).show();

                            userData.setVisibility(View.VISIBLE);
                            loggedOut.setVisibility(View.INVISIBLE);
                            loggedIn.setVisibility(View.INVISIBLE);
                            LoggedInUsername.setText(singleUserData[0]);
                            LoggedInEmail.setText(singleUserData[2]);
                            if(singleUserData.length==4){
                                SaveSharedPreference.setPrefUserImageUri(context,singleUserData[3]);
                                Uri uri=Uri.parse(singleUserData[3]);
                                personImage.setImageURI(uri);
                            }
                        }
                    }


                }
            });

            Register.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //NAME

                    EditText editName = (EditText) findViewById(R.id.RegisterName);
                    String registerName = (String) editName.getText().toString();
                    //

                    //EMAIL
                    EditText editEmail = (EditText) findViewById(R.id.registerEmail);
                    String registerEmail = (String) editEmail.getText().toString();
                    //

                    //Passwords //
                    EditText editPassword1 = (EditText) findViewById(R.id.registerPass);
                    String registerPassword1 = (String) editPassword1.getText().toString();

                    EditText editPassword2 = (EditText) findViewById(R.id.registerPass2);
                    String registerPassword2 = (String) editPassword2.getText().toString();
                    //


                    if (registerPassword1.matches(registerPassword2)) {
                        SaveSharedPreference.setUserName(context, registerName);
                       SaveSharedPreference.setPrefUserEmail(context,registerEmail);
                        fh.AddNewUser(context, registerName, registerPassword1, registerEmail);
                        Toast.makeText(context, "Sėkmingai užsiregistruota!",
                                Toast.LENGTH_SHORT).show();
                        userData.setVisibility(View.VISIBLE);
                        loggedOut.setVisibility(View.INVISIBLE);
                        loggedIn.setVisibility(View.INVISIBLE);
                        LoggedInUsername.setText(registerName);
                        LoggedInEmail.setText(registerEmail);

                    } else {
                        Toast.makeText(context, "Slaptažodžiai nesutampa",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });




        }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String name=menuItem.toString();
        int idas=menuItem.getItemId();
        SaveSharedPreference.setPrefActivityId(this,idas);
        System.out.println("ID : "+idas);

        if(menuItem.toString().contains("Home")){

            Intent Ketinimas = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Ketinimas);


        }
        if(menuItem.toString().contains("Calendar")){


            Intent Ketinimas = new Intent(getApplicationContext(), ViewCalendar.class);
            startActivity(Ketinimas);


        }
        if(menuItem.toString().contains("Sponsor")){

        }
        if(menuItem.toString()=="Profile"){

        }
        return false;
    }

    public void Click(View view) {
        loggedOut.setVisibility(View.INVISIBLE);
        loggedIn.setVisibility(View.VISIBLE);
}

    public void ToRegister(View view) {
        loggedOut.setVisibility(View.VISIBLE);
        loggedIn.setVisibility(View.INVISIBLE);
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            personImage.setImageURI(imageUri);
            SaveSharedPreference.setPrefUserImageUri(this,imageUri.toString());
            String username=SaveSharedPreference.getUserName(this);
            fh.addImageUriToCertainUser(username,this,imageUri.toString());


        }
    }
}
