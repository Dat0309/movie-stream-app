package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MainActivity extends AppCompatActivity implements MainRecycleAdapter.ListItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    BannerMovieAdapter bannerMovieAdapter;
    MainRecycleAdapter mainRecycleAdapter;
    TabLayout tabIndicater, tabCategory;
    ViewPager viewPager;
    RecyclerView categoryRecycle;
    List<BannerMovie> listHomeBanner, listTvShowBanner, listMovieBanner, listKidBanner;
    List<AllCategory> listCategory;
    List<CategoryItem> listItem1, listItem2, listItem3, listItem4, listItem5;
    DatabaseReference databaseReference;
    CircleImageView imgAvatar, imgUser;
    String avatar;
    FirebaseUser user;
    DrawerLayout drawerLayout;
    TextView txtName, txtUsername;
    NavigationView navigationView;
    Toolbar toolbar;
    public static final int FRAGMENT_FAVORITE = 1;
    int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequesPermissions();

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        imgAvatar = findViewById(R.id.imgAvatar);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (savedInstanceState != null){
            avatar = savedInstanceState.getString("avatar");
            Glide.with(MainActivity.this).load(avatar).error(R.drawable.user1).into(imgAvatar);
        }
        else if(avatar == null){
            avatar = user.getPhotoUrl().toString();
            Glide.with(MainActivity.this).load(avatar).error(R.drawable.user1).into(imgAvatar);
        }

        initUI();
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
        navigationView = findViewById(R.id.nav_user);
        txtName = navigationView.getHeaderView(0).findViewById(R.id.txtNavName);
        txtUsername = navigationView.getHeaderView(0).findViewById(R.id.txtUserName);
        tabIndicater = findViewById(R.id.tab_indicator);
        tabCategory = findViewById(R.id.tabCategory);
        imgUser = navigationView.getHeaderView(0).findViewById(R.id.img_user);

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


        navigationView.setNavigationItemSelectedListener(this);
        imgAvatar.setOnClickListener(this);
        showUserInfomation();
    }

    private void showUserInfomation(){

        if(user.getDisplayName().toString() == null){
            txtName.setVisibility(View.GONE);
        }
        else{
            txtName.setVisibility(View.VISIBLE);
            txtName.setText(user.getDisplayName().toString());
        }
        txtUsername.setText(user.getEmail().toString());
        Glide.with(MainActivity.this).load(user.getPhotoUrl().toString()).error(R.drawable.user1).into(imgUser);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgAvatar:
                showNavigationBar();
                break;
        }
    }

    private void showNavigationBar(){
        drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.nav_fav:
                startActivity(new Intent(MainActivity.this, FavoriteMovie.class));
                drawerLayout.closeDrawer(GravityCompat.END);
                break;

        }
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
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

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else{
            super.onBackPressed();
        }
    }
}