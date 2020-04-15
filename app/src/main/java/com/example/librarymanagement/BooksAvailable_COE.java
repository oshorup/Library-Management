package com.example.librarymanagement;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import DATA.BookDetails;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksAvailable_COE extends AppCompatActivity {
    @BindView(R.id.toolbar_BACOE)
    Toolbar toolbarBACOE;
    @BindView(R.id.Recycler_BACOE)
    RecyclerView RecyclerBACOE;
    @BindView(R.id.FAB_for_addNewBooks_BACOE)
    FloatingActionButton FABForAddNewBooksBACOE;
    @BindView(R.id.FAB_for_IssuedBooks_BACOE)
    FloatingActionButton FABForIssuedBooksBACOE;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference;
    @BindView(R.id.searchbooks)
    ImageView searchbooks;

    private BooksAddedAdapter mAdapter;
    // Layout manager is necessary because we need to display list of books vertically
    ArrayList<BookDetails> bookDetailslist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    ArrayList<BookDetails> filteredList = new ArrayList<>();

    ArrayList<String> result = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_available_in_coe);
        ButterKnife.bind(this);

        toolbarBACOE.setTitle("BOOKS AVAILABLE");
        toolbarBACOE.setTitleTextColor(Color.WHITE);
        toolbarBACOE.setNavigationIcon(R.drawable.back_arrow);
        toolbarBACOE.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reading Books...");


        sharedPreferences = getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        databaseReference = FirebaseDatabase.getInstance().getReference("BOOKS AVAILABLE");

        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerBACOE.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        readingBooks();

    }

    public void readingBooks() {

        progressDialog.show();
        bookDetailslist.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BookDetails bookDetails = dataSnapshot1.getValue(BookDetails.class);
                    bookDetailslist.add(bookDetails);
                }
                progressDialog.dismiss();

                    BooksAddedAdapter booksAddedAdapter = new BooksAddedAdapter(BooksAvailable_COE.this, bookDetailslist);
                    RecyclerBACOE.setAdapter(booksAddedAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @OnClick({R.id.FAB_for_addNewBooks_BACOE, R.id.FAB_for_IssuedBooks_BACOE})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.FAB_for_addNewBooks_BACOE:
                Intent ItANBCOE = new Intent(BooksAvailable_COE.this, BooksTobeAddedinLibrary_COE.class);
                startActivity(ItANBCOE);
                break;
            case R.id.FAB_for_IssuedBooks_BACOE:
                Intent ItBIDCOE = new Intent(BooksAvailable_COE.this, BooksIssued_COE.class);
                startActivity(ItBIDCOE);
                break;
        }
    }


    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String url = result.get(0);
                    openBrowser(url);
                }
                break;
        }
    }

    private void openBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, url);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_book);
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter=new BooksAddedAdapter();
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
