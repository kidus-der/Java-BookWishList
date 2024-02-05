package com.example.kidusder_mybookwishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// inner class BookViewHolder recommended by:
// Openai, ChatGPT, "Adapter class that provides reference to a view", 2024-02-04
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    //interface for the click and long click listeners
    public interface onItemClickListener {
        void onItemClick(Book book);
        void OnItemLongClick(Book book, int position);
    }

    private List<Book> book_list;
    private LayoutInflater inflater;
    onItemClickListener listener;

    // constructor for the adapter
    // the usage of context was recommended by:
    // Openai, ChatGPT, "Constructor for Adapter", 2024-02-04
    public BookAdapter(Context context, List<Book> book_list, onItemClickListener listener) {
        this.book_list = book_list;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        public TextView title_view, author_view, genre_view, read_status_view;

        // constructor for BookViewHolder
        public BookViewHolder(View itemView, final onItemClickListener listener) {

            super(itemView);

            this.title_view = itemView.findViewById((R.id.book_title_text));
            this.author_view = itemView.findViewById((R.id.book_author_name_text));
            this.genre_view = itemView.findViewById((R.id.book_genre_text));
            this.read_status_view = itemView.findViewById((R.id.book_read_status_text));

            // implementing onClick for user
            // clicking a book item on the view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(book_list.get(position));
                    }
                }
            }
            );

            // implementing the onLongClick
            // when the user wants to delete
            // a book from the wishlist
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnItemLongClick(book_list.get(position), position);
                }
                return true;
            });
        }

        // constructor to create new instance
        // in OnCreateViewHolder
        public BookViewHolder(View item_view) {
            super(item_view);
        }
    }

    // inflate the book_item xml layout
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item_view = inflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(item_view, listener); // initialize new instance of the view holder class
    }

    // display the data of the book
    // on the view
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Book current_book = book_list.get(position); // current book being viewed from the list

        // set the book data from current_book
        // to the text view of each attribute
        // of the book
        holder.title_view.setText(current_book.getTitle());
        holder.author_view.setText(current_book.getAuthor());
        holder.genre_view.setText(current_book.getGenre());
        // Openai, ChatGPT, "How to convert boolean value results into string"
        // 2024-02-04
        holder.read_status_view.setText(current_book.isRead() ? "Read" : "Unread");
    }

    // return how many books are in the wishlist
    @Override
    public int getItemCount() {
        return book_list.size();
    }

    // update the list of books
    // more efficient updates can be done instead of simply
    // notifying a change but I will try that
    // after I know this even works
    public void setBooks(List<Book> update_books) {

        book_list = update_books;
        notifyDataSetChanged();
    }
}
