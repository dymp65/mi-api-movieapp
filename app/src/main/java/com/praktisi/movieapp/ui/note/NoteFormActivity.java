package com.praktisi.movieapp.ui.note;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.praktisi.movieapp.R;
import com.praktisi.movieapp.databinding.ActivityNoteFormBinding;
import com.praktisi.movieapp.helpers.ViewHelper;
import com.praktisi.movieapp.models.Note;
import com.praktisi.movieapp.viewmodel.NoteViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteFormActivity extends AppCompatActivity {

    public static final String KEY_NOTE = "note";
    Note note;
    ActivityNoteFormBinding binding;
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNoteFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewHelper.setStatusBarColor(this, R.color.black_20, false);
        setToolbar();
        init();
        setView();
        setBtnClick();

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    private void init() {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        note = getIntent().getParcelableExtra(KEY_NOTE);
    }

    private void setView() {
        if (note != null) {
            binding.etNoteTitle.setText(note.getTitle());
            binding.etNoteDesc.setText(note.getDesc());
        }
    }

    private void setBtnClick() {
        binding.btnSaveNote.setOnClickListener(v -> {
            String title = binding.etNoteTitle.getText().toString();
            String desc = binding.etNoteDesc.getText().toString();

            if (validate(title, desc)) {
                saveNote(title, desc);
            }
        });
    }

    private boolean validate(String title, String desc) {
        if (title.isEmpty()) {
            binding.etNoteTitle.setError("Judul tidak boleh kosong!");
            binding.etNoteTitle.requestFocus();
            return false;
        }

        if (desc.isEmpty()) {
            binding.etNoteDesc.setError("Catatan tidak boleh kosong!");
            binding.etNoteDesc.requestFocus();
            return false;
        }

        return true;
    }

    private void saveNote(String title, String desc) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(currentDate);

        int id = 0;

        if (note != null) {
            id = note.getId();
        }

        Note note = new Note(
                formattedDateTime,
                 id,
                title,
                desc
        );

        if (note != null) {
            noteViewModel.updateNote(note);
            Toast.makeText(this, "Catatan berhasil diubah", Toast.LENGTH_SHORT).show();
        } else {
            noteViewModel.saveNote(note);
            Toast.makeText(this, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
    private void setToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_note_form);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}