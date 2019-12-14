package com.example.searchcermati;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class adapterSearch extends SuggestionsAdapter<modelUsers,adapterSearch.SuggestionHolder> {

    private Context context;
    private ArrayList<modelUsers> listUsers;

    adapterSearch(LayoutInflater inflater, Context context, ArrayList<modelUsers> listUsers) {
        super(inflater);
        this.context = context;
        this.listUsers = listUsers;
    }

    @Override
    public void onBindSuggestionHolder(final modelUsers modelUsers, SuggestionHolder suggestionHolder, int i) {

        suggestionHolder.username.setText(modelUsers.getUsername());
        suggestionHolder.url.setText(modelUsers.getUrl());
        ImageView imageView = suggestionHolder.avatar;
        Picasso.get()
                .load(modelUsers.getAvatar())
                .resize(60,60)
                .centerCrop()
                .into(imageView);
        suggestionHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = modelUsers.getUrl();
                Intent intent = new Intent(context, detail_user.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getSingleViewHeight() {
        return listUsers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString();
                if (term.isEmpty())
                    suggestions = suggestions_clone;
                else {
                    suggestions = new ArrayList<>();
                    for (modelUsers users : suggestions_clone)
                        if (users.getUsername().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(users);
                }
                results.values = suggestions;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<modelUsers>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.single_user, parent, false);
        return new SuggestionHolder(view);
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView url;
        ImageView avatar;
        CardView cardView;

        SuggestionHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_user);
            username = itemView.findViewById(R.id.username);
            url = itemView.findViewById(R.id.url);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
