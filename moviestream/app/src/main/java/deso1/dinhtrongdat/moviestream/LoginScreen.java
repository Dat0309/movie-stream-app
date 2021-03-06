package deso1.dinhtrongdat.moviestream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import deso1.dinhtrongdat.moviestream.data.DataUser;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;
import deso1.dinhtrongdat.moviestream.model.User;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    ImageView logoImg;
    TextView txtTitle, txtDes;
    TextInputLayout edtUser, edtPass;
    Button btnLogin, btnSignUp;
    ImageButton btnFB, btnGG;
    LinearLayout layout, layoutBtn;
    List<CategoryItem> favorites;
    String USER, PASS, IMG, NAME;
    List<User> listUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        initUI();
    }

    private void initUI() {
        logoImg = findViewById(R.id.logoImg);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtTitle = findViewById(R.id.txtTitle);
        txtDes = findViewById(R.id.txtDes);
        edtUser = findViewById(R.id.username);
        edtPass = findViewById(R.id.password);
        btnFB = findViewById(R.id.btn_login_facebook);
        btnGG = findViewById(R.id.btn_login_google);
        layout = findViewById(R.id.layout_login);
        layoutBtn = findViewById(R.id.linear_btn);

        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        Animation botAnim = AnimationUtils.loadAnimation(this, R.anim.bot_anim);

        logoImg.setAnimation(topAnim);
        txtTitle.setAnimation(topAnim);
        txtDes.setAnimation(topAnim);
        layoutBtn.setAnimation(botAnim);
        btnFB.setAnimation(botAnim);
        btnGG.setAnimation(botAnim);

        progressBar = (ProgressBar)findViewById(R.id.progres);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGG.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                if (!validationUser() | !validattionPass()) {
                    return;
                }
                isUser();
                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(layout, "layout_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginScreen.this, pairs);
                startActivity(intent, options.toBundle());
                break;
            case R.id.btn_login_facebook:
                break;
            case R.id.btn_login_google:
                startActivity(new Intent(LoginScreen.this, SignInGoogle.class));
                break;
        }
    }

    private void getListFavorite() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favorites = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CategoryItem item = ds.getValue(CategoryItem.class);
                    favorites.add(item);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LoginScreen.this, "Login Fail!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void isUser() {
        progressBar.setVisibility(View.VISIBLE);
        hideKeyboard(LoginScreen.this);
        favorites = new ArrayList<>();

        final String userName = edtUser.getEditText().getText().toString().trim();
        final String password = edtPass.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterable<DataSnapshot> nodeChild = snapshot.getChildren();
                for (DataSnapshot data : nodeChild) {
                    Map<String, Object> map = (Map<String, Object>) data.getValue();
                    USER = map.get("username").toString();
                    PASS = map.get("password").toString();
                    IMG = map.get("avatar").toString();
                    NAME = map.get("name").toString();

                    for(DataSnapshot ds : data.child("favorite").getChildren()){

                        CategoryItem item = ds.getValue(CategoryItem.class);
                        favorites.add(item);
                    }

                    if (userName.compareTo(USER) == 0) {
                        if (password.compareTo(PASS) == 0) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("img", IMG);
                            bundle.putString("name", NAME);
                            bundle.putSerializable("fav", (Serializable) favorites);

                            intent.putExtras(bundle);
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            finishAffinity();
                            break;
                        } else {
                            edtPass.setError("Sai m???t kh???u");
                            edtPass.requestFocus();
                        }
                    } else {
                        edtUser.setError("T??i kho???n kh??ng t???n t???i");
                        edtUser.requestFocus();
                    }
//                    User user = new User(USER, PASS, NAME, favorites, IMG);
//                    listUser.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }



    private boolean validationUser() {
        String val = edtUser.getEditText().getText().toString();
        String space = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            edtUser.setError("Nh???p t??i kho???n");
            return false;
        } else if (val.length() >= 15) {
            edtUser.setError("kh??ng ???????c qu?? 15 k?? t???");
            return false;
        } else if (!val.matches(space)) {
            edtUser.setError("Kh??ng ???????c ch???a kho???ng tr???ng");
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
            edtPass.setError("Nh???p m???t kh???u");
            return false;
        } else if (val.length() >= 15) {
            edtPass.setError("kh??ng ???????c qu?? 15 k?? t???");
            return false;
        } else if (!val.matches(space)) {
            edtPass.setError("Kh??ng ???????c ch???a kho???ng tr???ng");
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
}
