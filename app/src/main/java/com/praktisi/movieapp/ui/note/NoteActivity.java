package com.praktisi.movieapp.ui.note;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.praktisi.movieapp.R;
import com.praktisi.movieapp.adapter.NoteAdapter;
import com.praktisi.movieapp.databinding.ActivityNoteBinding;
import com.praktisi.movieapp.helpers.ViewHelper;
import com.praktisi.movieapp.models.Note;
import com.praktisi.movieapp.viewmodel.NoteViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements NoteAdapter.OnClickListener {

    ActivityNoteBinding binding;
    NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        ViewHelper.setStatusBarColor(this, R.color.black_20, false);
        setToolbar();

        binding.rvNote.setHasFixedSize(true);
        noteViewModel.getNote().observe(this, noteObserver);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    private final Observer<List<Note>> noteObserver = notes -> {
        NoteAdapter adapter = new NoteAdapter(this, notes);
        adapter.setOnClickListener(this, notes);
        adapter.setOnDeleteListener(this, notes);
        binding.rvNote.setAdapter(adapter);
    };
    private void setToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        FloatingActionButton fab = findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteFormActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(int position, List<Note> notes) {
        Note note = notes.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteFormActivity.KEY_NOTE, note);
        Intent intent = new Intent(this, NoteFormActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position, List<Note> notes) {
        Note note = notes.get(position);
        noteViewModel.deleteNote(note);
    }
}