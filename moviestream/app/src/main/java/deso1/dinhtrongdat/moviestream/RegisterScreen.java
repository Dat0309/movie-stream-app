package deso1.dinhtrongdat.moviestream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import deso1.dinhtrongdat.moviestream.model.CategoryItem;
import deso1.dinhtrongdat.moviestream.model.User;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnFB, btnGG, btnBack;
    ImageView imgLogo;
    TextView txtTitle, txtDes;
    TextInputLayout edtUser, edtPass, edtName;
    Button btnSignUp;
    LinearLayout linear;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_screen);

        initUI();
    }

    private void initUI() {
        btnFB = findViewById(R.id.btn_reg_facebook);
        btnGG = findViewById(R.id.btn_reg_google);
        btnBack = findViewById(R.id.btnBack);
        imgLogo = findViewById(R.id.logoSignUp);
        txtTitle = findViewById(R.id.txtTitleSignUp);
        txtDes = findViewById(R.id.txtDesSignUp);
        edtUser = findViewById(R.id.username_signup);
        edtPass = findViewById(R.id.password_signup);
        edtName = findViewById(R.id.name_signup);
        btnSignUp = findViewById(R.id.btnRegister);
        linear = findViewById(R.id.linear_signup);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        Animation botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_anim);
        Animation leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_anim);

        btnBack.setAnimation(leftAnim);
        imgLogo.setAnimation(topAnim);
        txtTitle.setAnimation(topAnim);
        txtDes.setAnimation(topAnim);
        linear.setAnimation(botAnim);
        btnFB.setAnimation(botAnim);
        btnGG.setAnimation(botAnim);

        progressBar = (ProgressBar)findViewById(R.id.progres);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        btnBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnFB.setOnClickListener(this);
        btnGG.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnRegister:
                progressBar.setVisibility(View.VISIBLE);
                hideKeyboard(RegisterScreen.this);
                if(!validateUser() |!validatePass()|!validateName()){
                    return;
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                //GET ALL VALUE
                String USER = edtUser.getEditText().getText().toString();
                String PASS = edtPass.getEditText().getText().toString();
                String NAME = edtName.getEditText().getText().toString();
                String IMG = "https://firebasestorage.googleapis.com/v0/b/moviestream-f6661.appspot.com/o/avatars%2Fuser1.jpg?alt=media&token=776839ac-1a14-47d9-a6fc-bd0b7314cbe8";

                List<CategoryItem> fav = null;

                User user = new User(USER, PASS,NAME,fav,IMG);
                reference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RegisterScreen.this, "Register Success!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(RegisterScreen.this, "Fail", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                finish();
                break;
            case R.id.btn_reg_facebook:
                startActivity(new Intent(RegisterScreen.this, RegisterFacebook.class));
                break;
            case R.id.btn_reg_google:
                startActivity(new Intent(RegisterScreen.this, RegisterGmail.class));
                break;
        }
    }

    private Boolean validateUser(){
        String val = edtUser.getEditText().getText().toString().trim();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            edtUser.setError("Nhập tài khoản");
            return false;
        }
        else if(val.length()>=15){
            edtUser.setError("không được quá 15 ký tự");
            return false;
        }
        else if(!val.matches(noWhiteSpace)){
            edtUser.setError("Không được chứa khoảng trắng");
            return false;
        }
        else{
            edtUser.setError(null);
            return true;
        }
    }

    private Boolean validatePass(){
        String val = edtPass.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            edtPass.setError("Nhập mật khẩu");
            return false;
        }
        else if(val.length()>=15){
            edtPass.setError("Không được quá 15 ký tự");
            return false;
        }
        else if(!val.matches(noWhiteSpace)){
            edtPass.setError("Không được chứa khoảng trắng");
            return false;
        }
        else{
            edtPass.setError(null);
            return true;
        }
    }
    private Boolean validateName(){
        String val = edtName.getEditText().getText().toString();

        if(val.isEmpty()){
            edtName.setError("Điền họ tên");
            return false;
        }
        else{
            edtName.setError(null);
            return true;
        }
    }

    private static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null){
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}