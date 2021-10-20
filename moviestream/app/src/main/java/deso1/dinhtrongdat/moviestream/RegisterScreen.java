package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnFB, btnGG, btnBack;
    ImageView imgLogo;
    TextView txtTitle, txtDes;
    TextInputLayout edtUser, edtPass;
    Button btnSignUp;
    LinearLayout linear;

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

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                startActivity(new Intent(this,LoginScreen.class));
                break;
        }
    }
}