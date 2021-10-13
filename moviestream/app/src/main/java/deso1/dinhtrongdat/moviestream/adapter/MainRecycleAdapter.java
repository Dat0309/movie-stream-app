package deso1.dinhtrongdat.moviestream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import deso1.dinhtrongdat.moviestream.R;
import deso1.dinhtrongdat.moviestream.model.AllCategory;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.MainViewHolder> {

    Context context;
    List<AllCategory> listCategory;

    public MainRecycleAdapter(Context context, List<AllCategory> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @Override
    public MainViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_category, null);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MainRecycleAdapter.MainViewHolder holder, int position) {
        AllCategory allCategory = listCategory.get(position);
        holder.txtTitle.setText(allCategory.getCateTitle());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        public MainViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
