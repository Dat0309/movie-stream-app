package deso1.dinhtrongdat.moviestream.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import deso1.dinhtrongdat.moviestream.MainActivity;
import deso1.dinhtrongdat.moviestream.R;

public class FavoriteFrg extends Fragment {
    Toolbar toolbar;
    ImageView imgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_favorite, container, false);
        toolbar = view.findViewById(R.id.toolbar_fav);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        imgBack = view.findViewById(R.id.imgBack_frg);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
