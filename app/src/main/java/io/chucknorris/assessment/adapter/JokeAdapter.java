package io.chucknorris.assessment.adapter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.models.Joke;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/25 at 4:47 PM
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {


    private Context context;
    private List<Joke> jokes;


    public JokeAdapter(Context context, List<Joke> jokes) {
        this.context = context;
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Joke joke = jokes.get(position);

        Glide.with(context)
                .load(joke.getIconURL())
                .into(holder.chuckImage);

        String category = "";
        if (!joke.getCategories().isEmpty()) {
            for (String currentCategory : joke.getCategories()) {
                category += currentCategory + "\n";
            }
        }

        if (!TextUtils.isEmpty(category))
            holder.text_category.setText(category);

        holder.text_createdAt.setText(joke.getCreatedAt().toString());
        holder.text_updatedAt.setText(joke.getUpdatedAt().toString());
        holder.text_joke.setText(joke.getValue());
    }

    @Override
    public int getItemCount() {
        return this.jokes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView chuckImage;
        TextView text_createdAt;
        TextView text_updatedAt;
        TextView text_category;
        TextView text_joke;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI();
        }

        private void initUI() {
            chuckImage = itemView.findViewById(R.id.chuck_image);
            text_createdAt = itemView.findViewById(R.id.text_created_at);
            text_updatedAt = itemView.findViewById(R.id.text_updated_at);
            text_category = itemView.findViewById(R.id.text_category);
            text_joke = itemView.findViewById(R.id.text_joke);
        }
    }
}
