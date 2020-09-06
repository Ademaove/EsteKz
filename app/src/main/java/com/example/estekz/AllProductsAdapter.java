package com.example.estekz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.estekz.models.OnlyNeededData;

import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

private static final int VIEW_TYPE_ITEM = 0;
private static final int VIEW_TYPE_LOADING = 1;

private List<OnlyNeededData> product;
private Context context;
private OnItemClickListener onItemClickListener;

        AllProductsAdapter(List<OnlyNeededData> product, Context context) {
        this.product = product;
        this.context = context;
        }

public interface OnItemClickListener {
    void onItemClick(View view, int position);
}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
            return new CustomViewHolder(view, onItemClickListener);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CustomViewHolder) {
            final CustomViewHolder customViewHolder = (CustomViewHolder) viewHolder;
            OnlyNeededData single_product = product.get(position);

            // Customize image loading with Glide - a media management and image loading framework for Android.
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(Utils.getRandomDrawableColor());
            requestOptions.error(Utils.getRandomDrawableColor());
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.centerCrop();

            Glide.with(context)
                    .load(single_product.getUrlToImage())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            customViewHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            customViewHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(customViewHolder.imageView);
            customViewHolder.title.setText(single_product.getName());
            customViewHolder.price.setText(single_product.getPrice()+" Тг");

        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return product == null ? 0 : product.size();
    }

    @Override
    public int getItemViewType(int position) {
        return product.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


/**
 * CustomViewHolder to hold Article items.
 */
public static class CustomViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    TextView title,  price;
    ImageView imageView;
    ProgressBar progressBar;
    Button button;
    OnItemClickListener onItemClickListener;

    CustomViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);

        itemView.setOnClickListener(this);
        title = itemView.findViewById(R.id.title);
        price = itemView.findViewById(R.id.price);
        imageView = itemView.findViewById(R.id.image);
        progressBar = itemView.findViewById(R.id.progress_load_photo);
        button=itemView.findViewById(R.id.to_shopping_cart);;


        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, getAdapterPosition());
    }
}

/**
 * Loading ViewHolder to show the ProgressBar while loading new elements.
 */
public static class LoadingViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}

}
