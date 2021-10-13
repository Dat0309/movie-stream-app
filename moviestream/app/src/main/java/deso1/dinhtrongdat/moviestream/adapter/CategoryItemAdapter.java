package deso1.dinhtrongdat.moviestream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import deso1.dinhtrongdat.moviestream.R;
import deso1.dinhtrongdat.moviestream.model.CategoryItem;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ItemViewHolder> {
    Context context;
    List<CategoryItem> listItem;

    public CategoryItemAdapter(Context context, List<CategoryItem> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CategoryItemAdapter.ItemViewHolder holder, int position) {
        CategoryItem categoryItem = listItem.get(position);
        Glide.with(context).load(categoryItem.getImgUrl()).into(holder.imgItem);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;

        public ItemViewHolder( View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
        }
    }
}
