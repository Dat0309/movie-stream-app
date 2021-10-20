package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import de.hdodenhof.circleimageview.CircleImageView;
import deso1.dinhtrongdat.moviestream.model.BannerMovie;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;

public class MovieDetail extends AppCompatActivity {
    CategoryItem CategoryItem;
    BannerMovie bannerItem;
    ImageView imgDetail,imgBack;
    TextView txtDetail;
    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initUI();
    }

    private void initUI() {
        CategoryItem = (CategoryItem) getIntent().getSerializableExtra("category_item");
        bannerItem = (BannerMovie)getIntent().getSerializableExtra("banner");
        imgDetail = findViewById(R.id.imgDetail);
        txtDetail = findViewById(R.id.txtNameMovie);
        btnPlay = findViewById(R.id.btnPlay);
        imgBack = findViewById(R.id.imgBack);


        if(CategoryItem != null){
            Glide.with(this).load(CategoryItem.getImg()).into(imgDetail);
            txtDetail.setText(CategoryItem.getName());
        }
        else{
            Glide.with(this).load(bannerItem.getImg()).into(imgDetail);
            txtDetail.setText(bannerItem.getName());
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetail.this, VideoPlayer.class);
                if(CategoryItem != null){
                    intent.putExtra("url", CategoryItem.getVideo());
                    startActivity(intent);
                }
                else{
                    intent.putExtra("url", bannerItem.getVideo());
                    startActivity(intent);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}