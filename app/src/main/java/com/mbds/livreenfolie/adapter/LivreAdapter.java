package com.mbds.livreenfolie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.mbds.livreenfolie.R;
import com.mbds.livreenfolie.activity.LivreDetailsActivity;
import com.mbds.livreenfolie.model.Livre;
import com.mbds.livreenfolie.util.Helpers;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LivreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Livre> articles;

    public LivreAdapter(List<Livre> articles) {
        super();
        this.articles = articles;
    }

    public void setData(List<Livre> articles) {
        this.articles = articles;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article, parent, false);
        return new LivreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LivreViewHolder viewHolder = (LivreViewHolder) holder;
        final Livre art = articles.get(position);

        viewHolder.bind(art);

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                String obj = gson.toJson(art);

                if (art != null && (art.getTitle() + "").length() > 2) {
                    Hawk.put("CURRENT_ARTICLE", art);
                    Context context = view.getContext();
                    Intent intent = new Intent(context, LivreDetailsActivity.class);
                    intent.putExtra("item_id", obj);

                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class LivreViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_title)
        public TextView articleTitle;

        @BindView(R.id.summary)
        public ImageButton summary;

        @BindView(R.id.article_date)
        public TextView articleDateTime;

        @BindView(R.id.article_image)
        public ImageView articleImage;

//        @BindView(R.id.category)
//        public TextView category;

        public final View mView;

        public LivreViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        void bind(Livre art) {
            articleTitle.setText(art.getTitle());

            try {

//                String body = art.getBody().substring(0, 30) + "...";
//                articleBody.setText(body);

                String name = art.getTitle() + "";

                if (name.length() > 15) {
                    name = name.substring(0, 12) + "...";
                }

                articleDateTime.setText(art.getAltDateTime() + ": " + name);

            } catch (Exception e) {
            }

            final String url = art.getImage() + "";
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.article)
                    .priority(Priority.HIGH);

            Glide.with(articleImage.getContext())
                    .load(url.trim())
                    .apply(options)
                    .into(articleImage);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + articleTitle.getText() + "'";
        }
    }
}
