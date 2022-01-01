package com.example.seniorsafety.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seniorsafety.R;
import com.example.seniorsafety.Score;

import java.util.List;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoresViewHolder> {
    private Context context;
    private List<Score> scores;

    public ScoresAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.score_row,parent,false);
        return new ScoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoresViewHolder holder, int position) {
        holder.tvUser.setText(scores.get(position).getScoreID());
        System.out.println("ID: "+scores.get(position).getScoreID());
       // holder.tvScore.setText(scores.get(position).getScore());

    }

    @Override
    public int getItemCount() {
        System.out.println("TAMANHO: "+this.scores.size());
        return this.scores.size();
    }

    public class ScoresViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser,tvScore;
        public ScoresViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser=itemView.findViewById(R.id.scoreName);
            tvScore=itemView.findViewById(R.id.scoreValue);
        }
    }
}
