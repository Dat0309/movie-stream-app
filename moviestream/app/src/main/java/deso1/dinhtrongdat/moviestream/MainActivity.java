package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import deso1.dinhtrongdat.moviestream.adapter.BannerMovieAdapter;
import deso1.dinhtrongdat.moviestream.adapter.MainRecycleAdapter;
import deso1.dinhtrongdat.moviestream.model.AllCategory;
import deso1.dinhtrongdat.moviestream.model.BannerMovie;

public class MainActivity extends AppCompatActivity {

    BannerMovieAdapter bannerMovieAdapter;
    MainRecycleAdapter mainRecycleAdapter;
    TabLayout tabIndicater, tabCategory;
    ViewPager viewPager;
    RecyclerView categoryRecycle;
    List<BannerMovie> listHomeBanner, listTvShowBanner, listMovieBanner, listKidBanner;
    List<AllCategory> listCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequesPermissions();
        initUI();
        bannerMovieAdapter.notifyDataSetChanged();
    }

    private void initUI() {
        tabIndicater = findViewById(R.id.tab_indicator);
        tabCategory = findViewById(R.id.tabCategory);
        categoryRecycle = findViewById(R.id.rcv_allcate);
        //list movie category Home
        listHomeBanner = new ArrayList<>();

        listHomeBanner.add(new BannerMovie(1,"INCEPTION","https://www.filmofilia.com/wp-content/uploads/2013/02/Inception-Banner.jpg",""));
        listHomeBanner.add(new BannerMovie(2,"STRANGER THINGS 3","https://th.bing.com/th/id/R.eb18cd279455442717d77a8a0da4a7c2?rik=z5AYS49g9WcG7Q&riu=http%3a%2f%2fwww.discorsivo.it%2fu%2fwp-content%2fuploads%2f2019%2f07%2fstranger-things-3.jpeg&ehk=HyjdZsD12%2fFLYdcrv3GnZVnwPrYJybcyP%2fOtBpbrPoU%3d&risl=&pid=ImgRaw&r=0",""));
        listHomeBanner.add(new BannerMovie(3,"SEX EDUCATION","https://th.bing.com/th/id/OIP.F0Eiuhx0XqNHC5v_1Vy0hwHaC2?pid=ImgDet&rs=1",""));
        listHomeBanner.add(new BannerMovie(4,"MONEY HEIST","https://i.ytimg.com/vi/H4seBIU6e8U/maxresdefault.jpg",""));
        listHomeBanner.add(new BannerMovie(5,"SQUID GAME","https://foto.kontan.co.id/etR1nlPuuQfYYYtwaLmPQLZdny0=/smart/2021/08/23/1397175347p.jpg",""));

        setBannerAdapter(listHomeBanner);

        listTvShowBanner = new ArrayList<>();
        listTvShowBanner.add(new BannerMovie(1,"FRIENDS","https://th.bing.com/th/id/R.f1cd7ee25ef3474233ff0acd10c27132?rik=hfv47E1uuqXFYw&riu=http%3a%2f%2fwww.endedtvseries.com%2fwp-content%2fuploads%2f2013%2f08%2ffriends-banner.bmp&ehk=p%2bMNVx%2fYQoMlUE21yagK5udson2DeAle7JATZSIm1es%3d&risl=&pid=ImgRaw&r=0",""));
        listTvShowBanner.add(new BannerMovie(2,"PEAKY BLINDERS","https://th.bing.com/th/id/R.21499facf04f3225d4b9395b730be21f?rik=GTbfMyT4vmIYBg&riu=http%3a%2f%2fwww.grapevinebirmingham.com%2fwp-content%2fuploads%2f2019%2f06%2fPEAKY-BLINDERS-ESCAPE-LIVE.jpg&ehk=HCqWkRfjGPCNzp%2bmuGPpH6adLJtj9I805LdUPWm8mpA%3d&risl=&pid=ImgRaw&r=0",""));
        listTvShowBanner.add(new BannerMovie(3,"LUCIFER","https://i.ytimg.com/vi/tC_J4qZqpuQ/maxresdefault.jpg",""));
        listTvShowBanner.add(new BannerMovie(4,"THE QUEEN'S GAMBIT","https://covers4.hosting-media.net/pixr/w398/radio/tile_radio_3138986.jpg",""));
        listTvShowBanner.add(new BannerMovie(5,"THE WITCHER","https://th.bing.com/th/id/R.134388fee42420423cfac04230072d6a?rik=295soNrYaNjSKg&pid=ImgRaw&r=0",""));

        listMovieBanner = new ArrayList<>();
        listMovieBanner.add(new BannerMovie(1,"JOHN WICH","https://2.bp.blogspot.com/-cGqZ0n2FHqc/WFaqcffUX2I/AAAAAAAAfvQ/8cSqjyYfFJc8UayQRRGn8MUMybJIDGKsgCLcB/s00/John_Wick_Chapter_2_Official_Banner_JPosters.jpg",""));
        listMovieBanner.add(new BannerMovie(2,"SPIDER MAN","https://1.bp.blogspot.com/-lr97Ebl6Ld0/XRSmULyfuTI/AAAAAAAAmM8/lr3pFGqU5jcMCZ713_KN4GPEieupu5C5ACLcBGAs/s1600/Flm_SpidermanFFH_Banner.png",""));
        listMovieBanner.add(new BannerMovie(3,"ENDGAME","https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/0859e11f-bd1a-4eba-b9a4-63a35c88a3c4/dd1zc9p-39086fd4-7aa8-4000-b5a1-ead3ca6a66bb.png/v1/fill/w_1192,h_670,q_70,strp/avengers_endgame_fan_banner_by_multificionado_dd1zc9p-pre.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MjI5NSIsInBhdGgiOiJcL2ZcLzA4NTllMTFmLWJkMWEtNGViYS1iOWE0LTYzYTM1Yzg4YTNjNFwvZGQxemM5cC0zOTA4NmZkNC03YWE4LTQwMDAtYjVhMS1lYWQzY2E2YTY2YmIucG5nIiwid2lkdGgiOiI8PTQwODAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.dAoNgv5D_A3dmxNCUTDaqwRtYPD7xteYj2WkwnU5LZI",""));
        listMovieBanner.add(new BannerMovie(4,"TRAIN TO BUSAN","https://th.bing.com/th/id/OIP.hqfLRzG-t0HjOKMOmKnKwQHaBX?pid=ImgDet&rs=1",""));
        listMovieBanner.add(new BannerMovie(5,"HARRY POTTER","https://i.ytimg.com/vi/HSl4zZRtQlw/maxresdefault.jpg",""));

        listKidBanner = new ArrayList<>();
        listKidBanner.add(new BannerMovie(1,"BLACK CLOVER","https://th.bing.com/th/id/R.ba420947a21b0b36d55a3e208261c13e?rik=cYj4UsmbJgiApg&riu=http%3a%2f%2fcdn.collider.com%2fwp-content%2fuploads%2f2017%2f10%2fblack-clover-banner.png&ehk=Dc1X8YWQyOLtcUNTFSDcR4RADcyEnUdcRTp0TeLrOQc%3d&risl=&pid=ImgRaw&r=0",""));
        listKidBanner.add(new BannerMovie(2,"DEMON SLAYER","https://i.pinimg.com/originals/65/c1/66/65c16665f39cbb90161dcf140c6cfce7.png",""));
        listKidBanner.add(new BannerMovie(3,"BORUTO","https://th.bing.com/th/id/OIP.C0IZpSA-LeoRxLVsQi6bygHaDW?pid=ImgDet&rs=1",""));
        listKidBanner.add(new BannerMovie(4,"ONE PUNCH MAN","https://th.bing.com/th/id/OIP.jLuYoggclW_2OCKjNGZy2gHaCx?pid=ImgDet&rs=1",""));
        listKidBanner.add(new BannerMovie(5,"FAIRY TAIL","https://th.bing.com/th/id/R.d81adc9de187232facfa8013edbaf58f?rik=Cv%2fvwFkEy7N%2fOA&riu=http%3a%2f%2f4.bp.blogspot.com%2f-jRUEh8bdhOM%2fUycDq2jO2jI%2fAAAAAAABdgM%2fWfE2vQ1_UMs%2fs1600%2fFairy-Tail-Banner-fairy-tail-31891192-1000-288.jpg&ehk=i4b7Dxop1OSuXd4w9mqz9OZYIcdGc%2bsNgx6pANVdNos%3d&risl=&pid=ImgRaw&r=0",""));

        listCategory = new ArrayList<>();
        listCategory.add(new AllCategory(1,))

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

    private void setBannerAdapter(List<BannerMovie> listBannerMovie) {
        viewPager = findViewById(R.id.bannerViewPager);
        bannerMovieAdapter =  new BannerMovieAdapter(MainActivity.this, listBannerMovie);
        viewPager.setAdapter(bannerMovieAdapter);
        bannerMovieAdapter.notifyDataSetChanged();
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