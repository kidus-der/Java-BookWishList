package com.example.kidusder_mybookwishlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddEditBookActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_books);

        // find the save and cancel buttons when
        // adding or deleting a book by their id
        Button save_button = findViewById(R.id.save_button);
        Button cancel_button = findViewById(R.id.cancel_button);

        // implement the drop down menu for the genre
        Spinner genreSpinner = findViewById(R.id.book_genre_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.book_genre_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);


        // click listener for save button
        save_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveBookToList();
            }
        });

        // click listener for cancel button
        cancel_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish(); // do nothing to the current state of the list
            }
        });
    }

    // method to save a book item to the book wishlist
    // either from an initial add or an edit to an
    // existing book item in the wishlist
    private void saveBookToList() {

        String title = ((EditText) findViewById(R.id.edit_book_title)).getText().toString();
        String author = ((EditText) findViewById(R.id.edit_author_name)).getText().toString();
        String genre = ((Spinner) findViewById(R.id.book_genre_spinner)).getSelectedItem().toString();
        String publicationYearString = ((EditText) findViewById(R.id.edit_book_publication_year)).getText().toString();
        boolean isRead = ((SwitchMaterial) findViewById(R.id.switch_read_status)).isChecked();

        // Openai, ChatGPT, "logic for validating input from user", 2024-02-04
        // Validate input
        if(title.isEmpty() || author.isEmpty() || genre.isEmpty() || publicationYearString.equals("0")) { //changed publication year to string comparison
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare result Intent
        Intent data = new Intent();
        data.putExtra("title", title);
        data.putExtra("author", author);
        data.putExtra("genre", genre);
        data.putExtra("publicationYear", publicationYearString);
        data.putExtra("isRead", isRead);

        setResult(RESULT_OK, data);

        finish();
    }
}
