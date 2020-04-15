package com.example.librarymanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DATA.BookDetails;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksTobeAddedinLibrary_COE extends AppCompatActivity {


    @BindView(R.id.toolbar_ANBCOE)
    Toolbar toolbarANBCOE;
    @BindView(R.id.accNo_ANBCOE)
    EditText accNoANBCOE;
    @BindView(R.id.isbn_ANBCOE)
    EditText isbnANBCOE;
    @BindView(R.id.book_name_ANBCOE)
    EditText bookNameANBCOE;
    @BindView(R.id.author_name_ANBCOE)
    EditText authorNameANBCOE;
    @BindView(R.id.publisher_coe_ANBCOE)
    EditText publisherCoeANBCOE;
    @BindView(R.id.d_id_ANBCOE)
    EditText dIdANBCOE;
    @BindView(R.id.rack_code_ANBCOE)
    EditText rackCodeANBCOE;
    @BindView(R.id.addBooksButton_ANBCOE)
    Button addBooksButtonANBCOE;
    @BindView(R.id.framelayout_ANBCOE)
    LinearLayout framelayoutANBCOE;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_books_in_coe);
        ButterKnife.bind(this);

        toolbarANBCOE.setTitle("NEW BOOK TO BE ADDED");
        toolbarANBCOE.setTitleTextColor(Color.WHITE);
        toolbarANBCOE.setNavigationIcon(R.drawable.back_arrow);
        toolbarANBCOE.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPreferences=getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference  = FirebaseDatabase.getInstance().getReference("BOOKS AVAILABLE");


    }

    @OnClick(R.id.addBooksButton_ANBCOE)
    public void onViewClicked() {

        progressDialog.setMessage("saving books");
        progressDialog.show();

        String accn = accNoANBCOE.getText().toString();
        String  isbn = isbnANBCOE.getText().toString();
        String  bookname = bookNameANBCOE.getText().toString();
        String authorname = authorNameANBCOE.getText().toString();
        String publisher = publisherCoeANBCOE.getText().toString();
        String did = dIdANBCOE.getText().toString();
        String reckid = rackCodeANBCOE.getText().toString();

        if(!accn.equalsIgnoreCase("")
                && !isbn.equalsIgnoreCase("")
                &&!bookname.equalsIgnoreCase("")
                && !authorname.equalsIgnoreCase("")
                && !publisher.equalsIgnoreCase("")
                && !did.equalsIgnoreCase("")
                && !reckid.equalsIgnoreCase("")){



            BookDetails bookDetails = new BookDetails(accn,isbn,bookname,authorname,publisher,did,reckid);
            databaseReference.child(accn).setValue(bookDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(BooksTobeAddedinLibrary_COE.this,"Book added to library successfully",Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(BooksTobeAddedinLibrary_COE.this,"Book not added, error"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }

                }
            });


        }else {
            Toast.makeText(BooksTobeAddedinLibrary_COE.this,"Anyone of book parameter is empty",Toast.LENGTH_SHORT).show();
        }


    }
}
