package deso1.dinhtrongdat.moviestream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import deso1.dinhtrongdat.moviestream.R;
import deso1.dinhtrongdat.moviestream.model.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    List<Favorite> listItem;

    public FavoriteAdapter(Context context, List<Favorite> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        Favorite favorite = listItem.get(position);

        holder.txtTitle.setText(favorite.getName());
        holder.txtType.setText(favorite.getType());
        Glide.with(context).load(favorite.getImg()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtType;
        ImageView imgPoster;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.tv_title_fav);
            txtType = itemView.findViewById(R.id.tv_type_fav);
            imgPoster = itemView.findViewById(R.id.iv_poster_fav);
        }
    }
}
