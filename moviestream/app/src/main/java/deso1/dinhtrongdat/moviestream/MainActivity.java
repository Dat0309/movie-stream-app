package deso1.dinhtrongdat.moviestream;

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

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import deso1.dinhtrongdat.moviestream.adapter.BannerMovieAdapter;
import deso1.dinhtrongdat.moviestream.adapter.MainRecycleAdapter;
import deso1.dinhtrongdat.moviestream.model.AllCategory;
import deso1.dinhtrongdat.moviestream.model.BannerMovie;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;

public class MainActivity extends AppCompatActivity implements MainRecycleAdapter.ListItemClickListener {

    BannerMovieAdapter bannerMovieAdapter;
    MainRecycleAdapter mainRecycleAdapter;
    TabLayout tabIndicater, tabCategory;
    ViewPager viewPager;
    RecyclerView categoryRecycle;
    List<BannerMovie> listHomeBanner, listTvShowBanner, listMovieBanner, listKidBanner;
    List<AllCategory> listCategory;
    List<CategoryItem> listItem1, listItem2, listItem3, listItem4, listItem5;

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

        //list item category
        listItem1 = new ArrayList<>();
        listItem1.add(new CategoryItem(1, "Squid Game", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABeT8BF61gSNC2oRBe6-JFhOWJhnuj_mfkv_wJgApSPBmAOeECsuMg5n0HlTw-RyaN6mrETar3Dtxv5ig5QZ-Cz1XbBsdAkjrG9uSfsKnTzIgR2EqB4Sr5FlQr6dh.jpg?r=756",""));
        listItem1.add(new CategoryItem(2,"Lucifer","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABTbMBcv-bUXlAOGWZyKO3ueIrpqQO9r3HHYJ58Qz7VDSg09xd9zarXjlWnuT_VmLGPEIF7nCSXPNDvU6G-zCc9_HBhMHMliWUuoGdoB3-gP79jLmsWKoQtsLmkxL.jpg?r=31e",""));
        listItem1.add(new CategoryItem(3, "Preaky Bliners", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABa4N_UskeG8z0GZWjLe_0WSMvusdEt6k2UrhYXPwxqUV_y-y46C0RzNtH8iGmcTGtw6q1dFiWloRqPGCNbfTNBY7LL22GfpoFrQ0V32pUADAX5t5A7MiqeIgv2RB.jpg?r=98c",""));
        listItem1.add(new CategoryItem(4,"Money Heist","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABQCcYpChthxsZQ5WGbjtoPKANvAlzetRttqwMu3n5gl1D4jisccCMbgAcFfBmwXIOfjQeCdIzDZ47FAkX85kZQTUKcISpo3Uio8WEhFWXhhMhw3yUBx-Z0ymyCVd.jpg?r=cfa",""));
        listItem1.add(new CategoryItem(5, "Sex Education", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABTVeFA1JppLhSJom-beAeGhbKhMEvyMevCXj8mEdgqWgqsJy9j1FfxFRjw49EX18y3nQ-_vQTO56RaUxryp5yiS5z4gcl7cQ8inF974S3SPB73uoayTjFlpUX9OskhzpXDY8aKtARquol_Ho0QW0lAoeNTYkRQ.jpg?r=41a",""));
        listItem1.add(new CategoryItem(6,"Friends","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABYryfSD2xXO3xKEPb6TJdccJ1pXIto2FWmwlmeiLno1w8qmKs-rieQp78QaimZFfnNFjgf8zqVT8PyORowGvzsUIzqE.jpg?r=75b",""));
        listItem1.add(new CategoryItem(7, "Stranger Things", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZfgHZMIuwHVuG033KMYHRi5sxkjPIfVxmoE-wfGl0_H7DPPdRNIyK4P1wlkD57yzra97xGQi1vX6zXL_kicQHrBylVcSUNFYhZm-7zZCyIW0D6_9391sHA8oseG.jpg?r=0dc",""));
        listItem1.add(new CategoryItem(8,"Prison Playbook","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABaDJ4AHABL9ORlz57CQG8EeXsisvBIVxW4GszAehVK3FpEDlKvQhT3_gKmfYxOGkpuddiMetxHnsa3twQ5nuGi3p5mA.jpg?r=13e",""));

        listItem2 = new ArrayList<>();
        listItem2.add(new CategoryItem(1,"Demon Slayer", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABTQrd0r_64Xg9GiguYpmweHsyABq9UNqphCSTqEJnHHWEXW9F93HM0VBAKL2tQFI2hjOFDhlXxJUkhQGD81ix5Q7g3u_xDyawN_VQTWaMBxiAjCYwq8OpdgjwrdudWYpcQ.jpg?r=be8",""));
        listItem2.add(new CategoryItem(2,"Black Clover", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABdGah_WpHZZv73ago2wAsrN3XATIDMz3NTDca28S_J-4z_3rkWkwuDTWT5Dp0jv-DNUFU5hfrGJieu8-ZO6RGlxixC8.jpg?r=567",""));
        listItem2.add(new CategoryItem(3,"Haikyu", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABZSjxUfuW1jl7XL007j2X2-wMZansHq-g3srvEJUkt23zvZNACJk4xtvXiCiQ3opTnqvdR4KAOJM-3jDv_9BtZsqxoA.jpg?r=5fe",""));
        listItem2.add(new CategoryItem(4,"Baki Hanma", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABc6JU5nkcOQ5N7mQiOY_0yNGkjTrqvyCG7QsXFvUpoFr-GBfKPHaNtH83oqMO6QWZ_NC0xUfTZgjgR6hDIGFKVx6Y0TKyvqazIXmerm2eFptOB-ccCc78m0ff9xZ.jpg?r=a08",""));
        listItem2.add(new CategoryItem(5,"Attack On Titan", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABbiaSKvuaU4YRInIXQF7kkypOpswMJyhVBEBZPqIz9IYo9dPmtvsIIZLq623LRWaOp7RiBBBgD63kpvT9T7w3nbUUfo.jpg?r=c0c",""));

        listItem3 = new ArrayList<>();
        listItem3.add(new CategoryItem(1, "Mr. & Mrs. Smith","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABU6rEXpqH0fJRZWerv2GEkAWWKFO133ls28cAWTwkhSw8QqkbmobXvzf1iT65a-GxuSF4W1zJJiqmYZiC8Fi3VbxOto.jpg?r=3aa",""));
        listItem3.add(new CategoryItem(2, "Aquaman","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABa1nyBEi4gqCXXYJXObKWDwHT3HARnPQTPIi1G0IoXNApiXdldb6HG-M6sAaT4iSEE9L-SljqzTf0jlJl6WRfLvCRYA.jpg?r=845",""));
        listItem3.add(new CategoryItem(3, "Demon Slayer: Mugen Train","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABTJDIa_xzSYhqnf-aPr2PHHjbL4XQDB9umNAdf_EGWdlkvLtwm9J1Ac0TzlJ_k9yzm7LCvpz_-MgMtign1llUg7nvDg.jpg?r=55b",""));
        listItem3.add(new CategoryItem(4, "Train to Busan","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWpqTz8-0pYg-qhERHxQeQVjQuRHMxxpzPHmg6uIQK14oiRQDek8hTMVM2p2sA0jJGBH7hqpTxHeQky9jSHtkYYs79s.jpg?r=e10",""));
        listItem3.add(new CategoryItem(5, "Fantastic Beats","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABbujozh68cRVFeFnlRor5V-Bmx3IoG4nXEvuS7s-5B7ulmSNaH3l5NZAlR6Ksvpj5B2OZoKYJMHear5Bd3B6Lja37c0.jpg?r=d6b",""));
        listItem3.add(new CategoryItem(6, "Warcraf","https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABSBGOHRqn3_dfPY7wjFVsXkbKcL6wcJjbOexfoHbvCnpW9KLYOnBXn4aB8uR2XgqEqBes545CiZOIJ2wo20Ld98vT8E.jpg?r=b46",""));

        listItem4 = new ArrayList<>();
        listItem4.add(new CategoryItem(1, "Spider-Man: Homecoming", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABRkoafH0jI9O_cEKqV-8YxKXqiaiahpufwxJrZFtW1QFmTRFq1DfO2v3uprZaiCzH-zJUli23mI9glQMGTSTQwsvafs.jpg?r=98a",""));
        listItem4.add(new CategoryItem(2, "John Wick", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABdjDqfbG-tQfChiLbWhmTGSUtHwPiQnQYbM11RUFqrZlQe_ECTTcBXq1qWxmFVkp0HgntUodIVgVU9xlo-nYRFXpi-4.jpg?r=108",""));
        listItem4.add(new CategoryItem(3, "Jurassic World", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABa85v-mk_WBILFHjeSVgOUBr2ExNKLvAX47QdJ-P2NdrxYjTN52co7OEYaJcmzjYOS-I1-TL3kU_rt3i9enYXDDtMjI.jpg?r=5a8",""));
        listItem4.add(new CategoryItem(4, "San Andreas", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABTFiGK_0Pcn28TFLtYXRc72gJbT6n6N8fY0H9pcRv06QQO9uRMPOrXPk8B9AI9jFoVRQ2PMYjwSgeouWRU_RWpNZM78.jpg?r=475",""));
        listItem4.add(new CategoryItem(5, "Rurouni Kenshin: The Final", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABe4_NWyiKhur5bFvun1h1OUSufpllaLv1HhzkGIfjiDGBjcwESsOmamGrdE71fZt82TxibGfyU-Is405JXDbCeyMNHX8snruGIkZyTYqjKjvob4xFrGPwhI8dYWo.jpg?r=d79",""));
        listItem4.add(new CategoryItem(6, "The Hobbit", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABcOxju6SAP2qS0ghnnUu-4vamkuv3R2i4RELrCeDbsYa1Bzs0pPaX_dZULP-iq-t82tQgEjRtFGrnRE1HPoFS5Jmv5w.jpg?r=78b",""));
        listItem4.add(new CategoryItem(7, "The Rebel", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABWIMcS6r31JD0RHXFWULUYvL2n5V5Di_lZMbSjzWcW9NY6tqLSO3zET9ShQg-WWuqex8YkotlUCRDeoHKOonkqda4rU.jpg?r=90c",""));

        listItem5 = new ArrayList<>();
        listItem5.add(new CategoryItem(1, "Show of Den", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABW6UJBZ3CfuQS05g6vuMSY9JcikwF9n3l2fAub8HBzqdfpQFcQ6DGx2imiGGanjO_tqIbamHqiy3SPmTWPSaJe4YOwc.jpg?r=560",""));
        listItem5.add(new CategoryItem(2, "Face Off 4", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABW9DnmPWcQ4ZsCwNjqyOYBa5wAsaHxf3nIU7uuIsw6C_2CarCtg6uM6PElBibPT-84RhV9PlB2Odbe3eCKEKynWAG2s.jpg?r=24e",""));
        listItem5.add(new CategoryItem(3, "Dream Eyes", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABS1uLwJgmIZlZAXYtHjpNxy25eTC3MRsVGVzrNuwEDXBc4xgEKn5qfTspgZFMUk2UL5G1pNlWjltDsk_Nf5K-mBNqxY.jpg?r=5d2",""));
        listItem5.add(new CategoryItem(4, "Pee Nak 2", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABeVtVHpH1XB_fQUy2TCGpkof9PCQSO3SqWQzMGr_rIII99okMc-0RXk0S3wavwGQiddves_5HnojzU0YETvB1Sl-m0M.jpg?r=164",""));
        listItem5.add(new CategoryItem(5, "Superstar Teacher", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABRb5Qq3z0LSaCgEoJqqfUhX3jheHXCHv3P6NgJScRog1CR3su6OCgwCuKS9zR93cSpZLtNPVgWkzl3adsIGjSst1tec.jpg?r=797",""));
        listItem5.add(new CategoryItem(6, "Pee Nak", "https://occ-0-395-58.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABb-yS9mHZ4epuKofjs99r68pxDn5onIjZ4rL0aJ_FjsB9Fj41RtwHIvUFHELS1C_ixWR8eAilTTXJehq9lqlSfHwC3E.jpg?r=3a8",""));

        //list category
        listCategory = new ArrayList<>();

        listCategory.add(new AllCategory(1,"TV Drama", listItem1));
        listCategory.add(new AllCategory(2,"Anime Series", listItem2));
        listCategory.add(new AllCategory(3,"Exciting Movies", listItem3));
        listCategory.add(new AllCategory(5,"Action Movies", listItem4));
        listCategory.add(new AllCategory(6,"Southeast Asian Movies", listItem5));

        setCategoryAdapter(listCategory);

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