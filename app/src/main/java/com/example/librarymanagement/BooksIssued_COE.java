package com.example.librarymanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import DATA.BookDetailsISSUED;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import DATA.BookDetails;
import DATA.BookDetailsISSUED;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksIssued_COE extends AppCompatActivity {

    @BindView(R.id.toolbar_BIDCOE)
    Toolbar toolbarBIDCOE;
    @BindView(R.id.Recycler_BIDCOE)
    RecyclerView RecyclerBIDCOE;
    @BindView(R.id.FAB_for_addNewBooksIssued_BIDCOE)
    FloatingActionButton FABForAddNewBooksIssuedBIDCOE;


    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference;

    // Layout manager is necessary because we need to display list of books vertically
    ArrayList<BookDetailsISSUED> bookDetailslistIssued = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_issued_in_coe);
        ButterKnife.bind(this);

        toolbarBIDCOE.setTitle("BOOKS ISSUED");
        toolbarBIDCOE.setTitleTextColor(Color.WHITE);
        toolbarBIDCOE.setNavigationIcon(R.drawable.back_arrow);
        toolbarBIDCOE.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Reading Books...");


        firebaseAuth=FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerBIDCOE.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        readingIssuedBooks();

    }

    public void readingIssuedBooks() {

        progressDialog.show();
        bookDetailslistIssued.clear();

        FirebaseUser curuser = firebaseAuth.getCurrentUser();
        String bid = curuser.getUid();

       // Toast.makeText(BooksIssued_COE.this,"bid is "+bid,Toast.LENGTH_LONG).show();
        databaseReference = FirebaseDatabase.getInstance().getReference("BOOKS ISSUED").child(bid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    BookDetailsISSUED bookDetailsIssued = dataSnapshot1.getValue(BookDetailsISSUED.class);
                    bookDetailslistIssued.add(bookDetailsIssued);
                }
                progressDialog.dismiss();
                AdapterBookIssued adapterBookIssued = new AdapterBookIssued(BooksIssued_COE.this,bookDetailslistIssued);
                RecyclerBIDCOE.setAdapter(adapterBookIssued);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @OnClick(R.id.FAB_for_addNewBooksIssued_BIDCOE)
    public void onViewClicked() {
        Intent ItBIDNCOE = new Intent(BooksIssued_COE.this,BooksTobeIssuedToBorrower_COE.class);
        startActivity(ItBIDNCOE);
    }
}
