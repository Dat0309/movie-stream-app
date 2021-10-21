package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFacebook extends AppCompatActivity {

    ImageButton btnBack;
    ImageView imgLogo;
    TextView txtTitle;
    TextInputLayout edtUser, edtPass;
    Button btnSignup;
    LinearLayout linear;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_facebook);

        initUI();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBackFB);
        imgLogo = findViewById(R.id.logoSignUpFB);
        txtTitle = findViewById(R.id.txtTitleSignUpFB);
        edtUser = findViewById(R.id.username_signupFB);
        edtPass = findViewById(R.id.password_signupFB);
        btnSignup = findViewById(R.id.btnRegisterFB);
        linear = findViewById(R.id.linear_signupFB);

        progressBar = (ProgressBar)findViewById(R.id.progres);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        Animation botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_anim);
        Animation leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_anim);

        btnBack.setAnimation(leftAnim);
        imgLogo.setAnimation(topAnim);
        txtTitle.setAnimation(topAnim);
        linear.setAnimation(botAnim);
    }
}