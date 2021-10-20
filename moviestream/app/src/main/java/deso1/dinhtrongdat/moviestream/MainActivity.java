package deso1.dinhtrongdat.moviestream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import deso1.dinhtrongdat.moviestream.adapter.BannerMovieAdapter;
import deso1.dinhtrongdat.moviestream.adapter.MainRecycleAdapter;
import deso1.dinhtrongdat.moviestream.model.AllCategory;
import deso1.dinhtrongdat.moviestream.model.BannerMovie;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;
import deso1.dinhtrongdat.moviestream.model.User;

public class MainActivity extends AppCompatActivity implements MainRecycleAdapter.ListItemClickListener {

    BannerMovieAdapter bannerMovieAdapter;
    MainRecycleAdapter mainRecycleAdapter;
    TabLayout tabIndicater, tabCategory;
    ViewPager viewPager;
    RecyclerView categoryRecycle;
    List<BannerMovie> listHomeBanner, listTvShowBanner, listMovieBanner, listKidBanner;
    List<AllCategory> listCategory;
    List<CategoryItem> listItem1, listItem2, listItem3, listItem4, listItem5;
    DatabaseReference databaseReference;
    CircleImageView imgAvatar;
    String avatar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequesPermissions();

        imgAvatar = findViewById(R.id.imgAvatar);

        if (savedInstanceState != null){
            avatar = savedInstanceState.getString("avatar");
            Glide.with(MainActivity.this).load(avatar).into(imgAvatar);
        }
        initUI();

        if(avatar == null)
            avatar = getIntent().getExtras().get("img").toString();
        Glide.with(MainActivity.this).load(avatar).into(imgAvatar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("avatar",avatar);
    }

    private void UpLoadMovie(){

        listItem1 = new ArrayList<>();
        listItem2 = new ArrayList<>();
        listItem3 = new ArrayList<>();
        listItem4 = new ArrayList<>();
        listItem5 = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("movies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    CategoryItem uploadCategoryItem = postSnapshot.getValue(CategoryItem.class);
                    if(uploadCategoryItem.getType().equals("TV Drama"))
                        listItem1.add(uploadCategoryItem);
                    if(uploadCategoryItem.getType().equals("Anime Series"))
                        listItem2.add(uploadCategoryItem);
                    if(uploadCategoryItem.getType().equals("Exciting Movies"))
                        listItem3.add(uploadCategoryItem);
                    if(uploadCategoryItem.getType().equals("Action Movies"))
                        listItem4.add(uploadCategoryItem);
                    if(uploadCategoryItem.getType().equals("Southeast Asian Movies"))
                        listItem5.add(uploadCategoryItem);
                }

                listCategory = new ArrayList<>();

                listCategory.add(new AllCategory(1,"TV Drama", listItem1));
                listCategory.add(new AllCategory(2,"Anime Series", listItem2));
                listCategory.add(new AllCategory(3,"Exciting Movies", listItem3));
                listCategory.add(new AllCategory(5,"Action Movies", listItem4));
                listCategory.add(new AllCategory(6,"Southeast Asian Movies", listItem5));

                setCategoryAdapter(listCategory);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private void initUI() {
        tabIndicater = findViewById(R.id.tab_indicator);
        tabCategory = findViewById(R.id.tabCategory);

        listHomeBanner = new ArrayList<>();
        listTvShowBanner = new ArrayList<>();
        listMovieBanner = new ArrayList<>();
        listKidBanner = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("banners");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BannerMovie uploadBanner = postSnapshot.getValue(BannerMovie.class);
                    if(uploadBanner.getType().equals("Home"))
                        listHomeBanner.add(uploadBanner);
                    if(uploadBanner.getType().equals("Tv Show"))
                        listTvShowBanner.add(uploadBanner);
                    if(uploadBanner.getType().equals("Movie"))
                        listMovieBanner.add(uploadBanner);
                    if(uploadBanner.getType().equals("Kids"))
                        listKidBanner.add(uploadBanner);
                }

                setBannerAdapter(listHomeBanner);

                tabCategory.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        switch (tab.getPosition()){
                            case 0:
                                setBannerAdapter(listHomeBanner);
                                break;
                            case 1:
                                setBannerAdapter(listTvShowBanner);
                                break;
                            case 2:
                                setBannerAdapter(listMovieBanner);
                                break;
                            case 3:
                                setBannerAdapter(listKidBanner);
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        UpLoadMovie();
    }

    private void setCategoryAdapter(List<AllCategory> listCategory) {
        categoryRecycle = findViewById(R.id.rcv_allcate);
        categoryRecycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mainRecycleAdapter = new MainRecycleAdapter(this, listCategory,this);
        mainRecycleAdapter.notifyDataSetChanged();
        categoryRecycle.setAdapter(mainRecycleAdapter);

    }

    private void setBannerAdapter(List<BannerMovie> listBannerMovie) {
        viewPager = findViewById(R.id.bannerViewPager);
        bannerMovieAdapter =  new BannerMovieAdapter(MainActivity.this, listBannerMovie);
        bannerMovieAdapter.notifyDataSetChanged();
        viewPager.setAdapter(bannerMovieAdapter);
        tabIndicater.setupWithViewPager(viewPager);

        Timer autoSlider = new Timer();
        autoSlider.schedule(new AutoSlider(listBannerMovie),4000, 6000);
        tabIndicater.setupWithViewPager(viewPager, true);
    }

    private void checkAndRequesPermissions(){
        String[] permissions = new String[]{
                Manifest.permission.INTERNET
        };

        List<String> listPermissionNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(permission);
            }
        }
        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),1);

        }
    }

    @Override
    public void onCategoryItemClick(int clickedItemIndex) {

    }

    public class AutoSlider extends TimerTask{

        List<BannerMovie> list;

        public AutoSlider(List<BannerMovie> list) {
            this.list = list;
        }

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(() ->{
                if(viewPager.getCurrentItem() < list.size()-1){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }
}