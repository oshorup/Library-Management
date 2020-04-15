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
import androidx.lifecycle.ViewModelStore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DATA.BookDetailsISSUED;
import DATA.UserInformation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksTobeIssuedToBorrower_COE extends AppCompatActivity {


    @BindView(R.id.toolbar_BIDNCOE)
    Toolbar toolbarBIDNCOE;
    @BindView(R.id.book_accNo_BIDNCOE)
    EditText bookAccNoBIDNCOE;
    @BindView(R.id.book_isbn_BIDNCOE)
    EditText bookIsbnBIDNCOE;
    @BindView(R.id.book_name_BIDNCOE)
    EditText bookNameBIDNCOE;
    @BindView(R.id.book_author_name_BIDNCOE)
    EditText bookAuthorNameBIDNCOE;
    @BindView(R.id.book_publisher_BIDNCOE)
    EditText bookPublisherBIDNCOE;
    @BindView(R.id.book_d_id_BIDNCOE)
    EditText bookDIdBIDNCOE;
    @BindView(R.id.book_borrower_BIDNCOE)
    EditText bookBorrowerBIDNCOE;
    @BindView(R.id.addBooksIssuedButton_BIDNCOE)
    Button addBooksIssuedButtonBIDNCOE;
    @BindView(R.id.framelayout_BIDNCOE)
    LinearLayout framelayoutBIDNCOE;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_books_issued_from_coe);
        ButterKnife.bind(this);

        toolbarBIDNCOE.setTitle("BOOK TO BE ISSUED");
        toolbarBIDNCOE.setTitleTextColor(Color.WHITE);
        toolbarBIDNCOE.setNavigationIcon(R.drawable.back_arrow);
        toolbarBIDNCOE.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPreferences=getSharedPreferences("LIBRARYMANAGERDATA", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        String  UID = firebaseAuth.getCurrentUser().getUid();


    }

    @OnClick(R.id.addBooksIssuedButton_BIDNCOE)
    public void onViewClicked() {

        progressDialog.setMessage("Issuing books...");
        progressDialog.show();
        String accn = bookAccNoBIDNCOE.getText().toString();
        String isbn = bookIsbnBIDNCOE.getText().toString();
        String bookname = bookNameBIDNCOE.getText().toString();
        String authorname = bookAuthorNameBIDNCOE.getText().toString();
        String publisher = bookPublisherBIDNCOE.getText().toString();
        String did = bookDIdBIDNCOE.getText().toString();
        String borrowerid = bookBorrowerBIDNCOE.getText().toString();




        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String  UID = currentuser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("BOOKS ISSUED").child(UID);

            if (!accn.equalsIgnoreCase("")
                    && !isbn.equalsIgnoreCase("")
                    && !bookname.equalsIgnoreCase("")
                    && !authorname.equalsIgnoreCase("")
                    && !publisher.equalsIgnoreCase("")
                    && !did.equalsIgnoreCase("")
                    && !borrowerid.equalsIgnoreCase("")) {

                String key = databaseReference.push().getKey();
                //String acc_nISD,String isbn,String bookname , String author,String publ,String did,String borrowerId,String key)
                BookDetailsISSUED bookDetailsISSUED = new BookDetailsISSUED(accn, isbn, bookname, authorname, publisher, did, borrowerid, key);
                databaseReference.child(key).setValue(bookDetailsISSUED).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(BooksTobeIssuedToBorrower_COE.this, "Book issued successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            ;
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(BooksTobeIssuedToBorrower_COE.this, "Book not issued " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(BooksTobeIssuedToBorrower_COE.this, "anyone of the book parameter is empty", Toast.LENGTH_SHORT).show();

            }


        }

    }


