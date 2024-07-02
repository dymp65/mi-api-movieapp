package com.praktisi.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.praktisi.movieapp.R;
import com.praktisi.movieapp.models.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private Context context;
    private OnClickListener onClickListener;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public interface OnClickListener {
        void onClick(int position, List<Note> notes);
        void onDelete(int position, List<Note> notes);
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvDate;
        ImageView ivDelete;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_note_title);
            tvDesc = itemView.findViewById(R.id.tv_note_overview);
            tvDate = itemView.findViewById(R.id.tv_note_date);
            ivDelete = itemView.findViewById(R.id.iv_delete);

            if (onClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onClickListener.onClick(getAdapterPosition(), notes);
                });

                ivDelete.setOnClickListener(v -> {
                    onClickListener.onDelete(getAdapterPosition(), notes);
                });
            }
        }

        public void bind(Note note) {
            tvTitle.setText(note.getTitle());
            tvDesc.setText(note.getDesc());
            tvDate.setText(note.getDate());
        }
    }

    public void setOnClickListener(OnClickListener onClickListener, List<Note> notes) {
        this.onClickListener = onClickListener;
        this.notes = notes;
    }

    public void setOnDeleteListener(OnClickListener onClickListener, List<Note> notes) {
        this.onClickListener = onClickListener;
        this.notes = notes;
    }
}
