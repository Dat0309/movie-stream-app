package deso1.dinhtrongdat.moviestream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import deso1.dinhtrongdat.moviestream.model.BannerMovie;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;
import deso1.dinhtrongdat.moviestream.model.Favorite;

public class MovieDetail extends AppCompatActivity implements View.OnClickListener {
    CategoryItem categoryItem;
    BannerMovie bannerItem;
    Favorite favoriteItem;
    ImageView imgDetail, imgBack, imgAdd;
    TextView txtDetail;
    Button btnPlay;
    FirebaseUser user;
    DatabaseReference reference;
    boolean ADD = true;
    boolean UnADD = false;
    boolean IS_ADD = UnADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        favoriteItem = (Favorite) getIntent().getSerializableExtra("favorite_item");
        categoryItem = (CategoryItem) getIntent().getSerializableExtra("category_item");
        bannerItem = (BannerMovie) getIntent().getSerializableExtra("banner");

        initUI();
    }

    private void initUI() {
        imgDetail = findViewById(R.id.imgDetail);
        txtDetail = findViewById(R.id.txtNameMovie);
        btnPlay = findViewById(R.id.btnPlay);
        imgBack = findViewById(R.id.imgBack);
        imgAdd = findViewById(R.id.imgAdd);
        checkFavorite();


        if (categoryItem != null) {
            Glide.with(this).load(categoryItem.getImg()).into(imgDetail);
            txtDetail.setText(categoryItem.getName());
        } else if(bannerItem != null){
            Glide.with(this).load(bannerItem.getImg()).into(imgDetail);
            txtDetail.setText(bannerItem.getName());
        }
        else{
            Glide.with(this).load(favoriteItem.getImg()).into(imgDetail);
            txtDetail.setText(favoriteItem.getName());
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetail.this, VideoPlayer.class);
                if (categoryItem != null) {
                    intent.putExtra("url", categoryItem.getVideo());
                    startActivity(intent);
                } else if(bannerItem!=null){
                    intent.putExtra("url", bannerItem.getVideo());
                    startActivity(intent);
                }else{
                    intent.putExtra("url",favoriteItem.getVideo());
                    startActivity(intent);
                }
            }
        });

        imgBack.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgAdd:
                if (!IS_ADD) {
                    addToFavorite(user);
                }
                else{
                    Toast.makeText(MovieDetail.this, "Phim đã tồn tại trong mục ưa thích", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void checkFavorite(){
        Favorite currenFavorite;
        if(categoryItem!= null) {
            currenFavorite = new Favorite(categoryItem.getId(), categoryItem.getName(), categoryItem.getImg(), categoryItem.getType(), categoryItem.getVideo(), user.getUid());
        }else if(bannerItem!=null){
            currenFavorite = new Favorite(bannerItem.getId(), bannerItem.getName(), bannerItem.getImg(), bannerItem.getType(), bannerItem.getVideo(), user.getUid());
        }
        else{
            currenFavorite = favoriteItem;
        }
        reference = FirebaseDatabase.getInstance().getReference("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){

                    Favorite favorite = data.getValue(Favorite.class);

                    if(favorite.getUid().compareTo(currenFavorite.getUid())==0){
                        if(String.valueOf(favorite.getId()).compareTo(String.valueOf(currenFavorite.getId()))==0){
                            IS_ADD = ADD;
                            imgAdd.setImageResource(R.drawable.ic_baseline_add_red);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void removeFavorite(FirebaseUser user) {
        Favorite currenFavorite;
        if(categoryItem!= null) {
            currenFavorite = new Favorite(categoryItem.getId(), categoryItem.getName(), categoryItem.getImg(), categoryItem.getType(), categoryItem.getVideo(), user.getUid());
        }else if(bannerItem!=null){
            currenFavorite = new Favorite(bannerItem.getId(), bannerItem.getName(), bannerItem.getImg(), bannerItem.getType(), bannerItem.getVideo(), user.getUid());
        }
        else{
            currenFavorite = favoriteItem;
        }
        reference = FirebaseDatabase.getInstance().getReference("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){

                    Favorite favorite = data.getValue(Favorite.class);

                    if(favorite.getUid().compareTo(currenFavorite.getUid())==0){
                        if(String.valueOf(favorite.getId()).compareTo(String.valueOf(currenFavorite.getId()))==0){
                            reference.child(data.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MovieDetail.this, "Đã xóa khỏi mục ưa thích", Toast.LENGTH_LONG).show();
                                        IS_ADD = UnADD;
                                        imgAdd.setImageResource(R.drawable.ic_baseline_add_24);
                                    } else {
                                        Toast.makeText(MovieDetail.this, "Thất bại", Toast.LENGTH_LONG).show();
                                        IS_ADD = ADD;
                                        imgAdd.setImageResource(R.drawable.ic_baseline_add_red);
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void addToFavorite(FirebaseUser user) {
        Favorite favorite;
        if(categoryItem!= null) {
            favorite = new Favorite(categoryItem.getId(), categoryItem.getName(), categoryItem.getImg(), categoryItem.getType(), categoryItem.getVideo(), user.getUid());
        }else if(bannerItem!=null){
            favorite = new Favorite(bannerItem.getId(), bannerItem.getName(), bannerItem.getImg(), bannerItem.getType(), bannerItem.getVideo(), user.getUid());
        }else{
            favorite = favoriteItem;
        }

        reference = FirebaseDatabase.getInstance().getReference("favorite");
        reference.push().setValue(favorite).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MovieDetail.this, "Đã thêm vào mục yêu thích", Toast.LENGTH_LONG).show();
                    IS_ADD = ADD;
                    imgAdd.setImageResource(R.drawable.ic_baseline_add_red);
                } else {
                    Toast.makeText(MovieDetail.this, "Thất bại", Toast.LENGTH_LONG).show();
                    IS_ADD = UnADD;
                    imgAdd.setImageResource(R.drawable.ic_baseline_add_24);
                }
            }
        });
    }
}
