package deso1.dinhtrongdat.moviestream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import deso1.dinhtrongdat.moviestream.adapter.FavoriteAdapter;
import deso1.dinhtrongdat.moviestream.model.Favorite;

public class FavoriteMovie extends AppCompatActivity implements View.OnClickListener {

    ImageView imgBack;
    FavoriteAdapter adapter;
    RecyclerView rcvFav;
    List<Favorite> mdata;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        
        initUI();
    }

    private void initUI() {
        mdata = new ArrayList<>();
        imgBack = findViewById(R.id.imgBack_frg);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rcvFav = findViewById(R.id.recycle_fav);
        rcvFav.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        reference = FirebaseDatabase.getInstance().getReference("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Favorite favorite = data.getValue(Favorite.class);
                    if(favorite.getUid().compareTo(user.getUid())==0){
                        mdata.add(favorite);
                        adapter = new FavoriteAdapter(FavoriteMovie.this, mdata);
                        adapter.notifyDataSetChanged();

                        rcvFav.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        imgBack.setOnClickListener(this);
    }

    private void swipe(){
        
    }

    private List<Favorite> getFavoriteList(FirebaseUser user) {
        List<Favorite> list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    Favorite favorite = data.getValue(Favorite.class);
                    if(favorite.getUid().equals(user.getUid().toString())){
                        list.add(favorite);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack_frg:
                finish();
                break;
        }
    }

}