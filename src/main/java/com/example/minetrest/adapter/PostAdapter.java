package com.example.minetrest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minetrest.R;
import com.example.minetrest.postContent;
import com.example.minetrest.posts.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>
{
    private Context context;
    private Post[] posts;

    public PostAdapter(Context context, Post[] posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostHolder holder, int position) {
        final Post post=posts[position];
        holder.textView.setText(post.getTitle());

        Document document= Jsoup.parse(post.getContent());
        Elements elements=document.select("img");
        Glide.with(context).load(elements.get(0).attr("src")).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, postContent.class);

                intent.putExtra("url",post.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
