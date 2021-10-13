package deso1.dinhtrongdat.moviestream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import deso1.dinhtrongdat.moviestream.R;
import deso1.dinhtrongdat.moviestream.model.BannerMovie;

public class BannerMovieAdapter extends PagerAdapter {
    Context context;
    List<BannerMovie> listBanner;

    public BannerMovieAdapter(Context context, List<BannerMovie> listBanner) {
        this.context = context;
        this.listBanner = listBanner;
    }

    @Override
    public int getCount() {
        return listBanner.size();
    }

    @Override
    public boolean isViewFromObject( View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie, null);
        ImageView imgBanner = view.findViewById(R.id.img_banner);
        Glide.with(context).load(listBanner.get(position).getImgUrl()).into(imgBanner);
        container.addView(view);
        return view;
    }
}
