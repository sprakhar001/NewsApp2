package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp2.R;

import model.Article;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsRecyclerViewHolder> {
    private ArticleClickListener listener;

    public NewsRecyclerViewAdapter(ArticleClickListener listener) {
        this.listener = listener;
    }

    public interface ArticleClickListener{
        void articleTapped(Article article);
    }

    public static final DiffUtil.ItemCallback<Article> diffCallBack = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getUrl() == newItem.getUrl();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getPublishedAt().equals(newItem.getPublishedAt()) && oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    public final AsyncListDiffer<Article> listDiffer = new AsyncListDiffer<Article>(this, diffCallBack);

    @NonNull
    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_view_item, parent, false);
        return new NewsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewHolder holder, int position) {
        Article article = listDiffer.getCurrentList().get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPublishedAt());
        Glide.with(holder.itemView).load(article.getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public class NewsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, date;
        public ImageView imageView;

        public NewsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_title);
            date = (TextView) itemView.findViewById(R.id.news_date);
            imageView = (ImageView) itemView.findViewById(R.id.news_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Article article = listDiffer.getCurrentList().get(getAdapterPosition());
            listener.articleTapped(article);
        }
    }
}
