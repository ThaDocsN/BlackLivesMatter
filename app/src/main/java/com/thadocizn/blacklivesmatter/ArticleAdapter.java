package com.thadocizn.blacklivesmatter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {
    private List<Article> articlesList;
    private Context context;
    private ItemClickListener clickListener;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.articlesList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView section;
        TextView articleTitle;
        TextView publishDate;

        private MyViewHolder(View itemView) {
            super(itemView);

            section = itemView.findViewById(R.id.section);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            publishDate = itemView.findViewById(R.id.publishDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());

        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.MyViewHolder holder, int position) {
        Article currentArticle = articlesList.get(position);

        holder.section.setText(currentArticle.getSection());
        holder.articleTitle.setText(currentArticle.getArticleTitle());
        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentArticle.getPublicationDate());
        String formattedDateTime = formatDateTime(dateObject);
        holder.publishDate.setText(formattedDateTime);
    }

    private String formatDateTime(Date dateObject) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("LLL dd, yyyy, h:mm a");
        return dateTimeFormat.format(dateObject);
    }

    @Override
    public int getItemCount() {
        if (articlesList == null) {
            return 0;
        } else { return articlesList.size(); }
    }

    public void setArticleInfoList(List<Article> articlesList) {
        this.articlesList = articlesList;
    }
}
