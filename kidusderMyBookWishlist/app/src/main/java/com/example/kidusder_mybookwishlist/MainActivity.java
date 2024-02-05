package com.example.kidusder_mybookwishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookAdapter.onItemClickListener {

    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> book_list = new ArrayList<>();
    private static final int ADD_BOOK_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the floating action button
        FloatingActionButton addBookButton = findViewById(R.id.add_book_fab);
        addBookButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            // Openai, ChatGPT, "How do I start another activity in MainActivity), 2024-02-04
            startActivityForResult(intent, ADD_BOOK_REQUEST); // I need to find an alternative to using this
        });

        // set up the RecyclerView and the Adapter
        booksRecyclerView = findViewById(R.id.books_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, book_list, this);
        booksRecyclerView.setAdapter(bookAdapter);

        // update the book count when a new book item is created
        updateBookCountTextView();
    }

    // when an object is clicked
    @Override
    public void onItemClick(Book book) {

        Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
        intent.putExtra("bookTitle", book.getTitle());
        intent.putExtra("bookAuthor", book.getAuthor());
        intent.putExtra("bookGenre", book.getGenre());
        intent.putExtra("bookPublicationYear", book.getPublicationYear());
        intent.putExtra("bookReadStatus", book.isRead());
        startActivity(intent);
    }

    // when a book is long pressed from the list
    @Override
    public void OnItemLongClick(Book book, int position) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Selected Book")
                .setMessage("Confirm")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    book_list.remove(position); // remove the indexed book from the wishlist
                    bookAdapter.notifyItemRemoved(position); //update the book adapter
                    updateBookCountTextView(); //update the count of books in the text view
                    Toast.makeText(MainActivity.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Openai, ChatGPT, "logic for checking if a new object was added", 2024-02-04
        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra("title");
            String author = data.getStringExtra("author");
            String genre = data.getStringExtra("genre");
            int publicationYear = data.getIntExtra("publicationYear", 0);
            boolean isRead = data.getBooleanExtra("isRead", false);

            Book new_book = new Book(title, author, genre, publicationYear, isRead);
            book_list.add(new_book); // add the new book object to the wishlist
            bookAdapter.notifyDataSetChanged();
            updateBookCountTextView(); // Update the count
        }
    }


    // update the total book count and read books
    // in the text view
    private void updateBookCountTextView() {

        int totalCount = book_list.size(); // total number of books
        int readCount = (int) book_list.stream().filter(Book::isRead).count(); // count of books marked as read

        // I need to make a string book_count_text in the strings.xml
        String bookCountText = getString(R.string.book_count_text, totalCount, readCount);
        TextView bookCountTextView = findViewById(R.id.book_count);
        bookCountTextView.setText(bookCountText);
    }
}