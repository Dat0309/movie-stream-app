package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignInGoogle extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack;
    ImageView logo;
    TextView txtTitle;
    TextInputLayout edtUser, edtPass;
    Button btnLogin;
    ProgressBar progressBar;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_google);

        initUI();
    }

    private void initUI() {
        btnBack = findViewById(R.id.btnBack_signGG);
        logo = findViewById(R.id.logoSignInGG);
        txtTitle = findViewById(R.id.txtTitleSignInGG);
        edtUser = findViewById(R.id.username_signinGG);
        edtPass = findViewById(R.id.password_signinGG);
        btnLogin = findViewById(R.id.btnLoginGG);
        linear = findViewById(R.id.linear_signInGG);

        progressBar = (ProgressBar)findViewById(R.id.progres);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        Animation botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_anim);
        Animation leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_anim);

        btnBack.setAnimation(leftAnim);
        logo.setAnimation(topAnim);
        txtTitle.setAnimation(topAnim);
        linear.setAnimation(botAnim);

        btnLogin.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void signInGoogle(){
        if(!validationUser() | !validattionPass()){
            return;
        }
        hideKeyboard(SignInGoogle.this);
        progressBar.setVisibility(View.VISIBLE);

        String strEmail = edtUser.getEditText().getText().toString().trim();
        String strPass = edtPass.getEditText().getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignInGoogle.this, "Success!!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = auth.getCurrentUser();

                    Intent intent = new Intent(SignInGoogle.this, MainActivity.class);
                    startActivity(intent);

                    finishAffinity();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(SignInGoogle.this, "Fail!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean validationUser() {
        String val = edtUser.getEditText().getText().toString();
        String space = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtUser.setError("Nhập tài khoản");
            return false;
        } else {
            edtUser.setError(null);
            return true;
        }
    }

    private boolean validattionPass() {
        String val = edtPass.getEditText().getText().toString();
        String space = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtPass.setError("Nhập mật khẩu");
            return false;
        } else if (val.length() >= 15) {
            edtPass.setError("không được quá 15 ký tự");
            return false;
        } else if (!val.matches(space)) {
            edtPass.setError("Không được chứa khoảng trắng");
            return false;
        } else {
            edtPass.setError(null);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoginGG:
                signInGoogle();
                break;
            case R.id.btnBackGG:
                finish();
                break;
        }
    }
}