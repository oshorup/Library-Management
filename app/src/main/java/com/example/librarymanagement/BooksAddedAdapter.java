package com.example.librarymanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DATA.BookDetails;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BooksAddedAdapter extends RecyclerView.Adapter<BooksAddedAdapter.BooksAddedHolder> implements Filterable {

    Context context;
    ArrayList<BookDetails> booklist = new ArrayList<>();
    ArrayList<BookDetails> booklistExample;

    UpdateBookDetails updateBookDetails;
    public BooksAddedAdapter(Context con, ArrayList<BookDetails> list) {
        this.context = con;
        this.booklist = list;
        booklistExample=new ArrayList<>(list);

        updateBookDetails=(UpdateBookDetails)context;
    }
    BooksAddedAdapter()
    {

    }


    @NonNull
    @Override
    public BooksAddedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewobject = LayoutInflater.from(context).inflate(R.layout.row_recycler_all_books_added, parent, false);
        BooksAddedHolder booksAddedHolder = new BooksAddedHolder(viewobject);
        return booksAddedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAddedHolder holder, int position) {

        // taking all values from BoookDetails class
        BookDetails bookDetails = booklist.get(position);

        String accn = bookDetails.getAcc_n();
        String isbn = bookDetails.getI_sbn();
        String bookname = bookDetails.getBook_name();
        String authorname = bookDetails.getAuthor_name();
        String publisher = bookDetails.getPublisher_name();
        String did = bookDetails.getD_id();
        String reckid = bookDetails.getRack_code();

        // now setting all values to holder
        holder.textRowAccountnumber.setText(accn);
        holder.textRowIsbn.setText(isbn);
        holder.textRowBookname.setText(bookname);
        holder.textRowAuthorname.setText(authorname);
        holder.textRowPublisher.setText(publisher);
        holder.textRowDId.setText(did);
        holder.textRowRackcode.setText(reckid);
        // now our adapter is ready for displaying

        // now working for editing data
        holder.imageRowEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetails bookDetails1=booklist.get(position);
                showDialog(bookDetails1);

            }
        });

        // now working for deleting books from library
        holder.imageRowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookDetails bookDetails1=booklist.get(position);
                showDialogForDelete(bookDetails1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return booklist.size();
    }

    // below function is useless, i will use it in future
    @OnClick({R.id.image_row_edit, R.id.image_row_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_row_edit:
                break;
            case R.id.image_row_delete:
                break;
        }
    }

    public class BooksAddedHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_row_accountnumber)
        TextView textRowAccountnumber;
        @BindView(R.id.text_row_Isbn)
        TextView textRowIsbn;
        @BindView(R.id.text_row_Bookname)
        TextView textRowBookname;
        @BindView(R.id.text_row_authorname)
        TextView textRowAuthorname;
        @BindView(R.id.text_row_Publisher)
        TextView textRowPublisher;
        @BindView(R.id.text_row_D_id)
        TextView textRowDId;
        @BindView(R.id.text_row_Rackcode)
        TextView textRowRackcode;
        @BindView(R.id.image_row_delete)
        ImageView imageRowDelete;
        @BindView(R.id.image_row_edit)
        ImageView imageRowEdit;


        public BooksAddedHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, this.itemView);
        }
    }

    @Override // function inside Filterable interface
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // this function will be filtering automatically in background
            ArrayList<BookDetails> filteredlist=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredlist.addAll(booklistExample);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(BookDetails item : booklistExample)
                {
                    if(item.getBook_name().toLowerCase().contains(filterPattern))
                    {
                        filteredlist.add(item);
                    }

                }
            }

            FilterResults res = new FilterResults();
            res.values=filteredlist;
            return res;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            booklist.clear();
            booklist.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    }; // filtering works end here

    public void showDialog(BookDetails bookDetails)
    {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_layout_book_available);
        dialog.show(); // by now our dialog will pop up on calling this function

        EditText bookname = (EditText)dialog.findViewById(R.id.addbooks_update_bookname);
        EditText author = (EditText)dialog.findViewById(R.id.addbooks_update_author);
        EditText publisher = (EditText)dialog.findViewById(R.id.addbooks_update_publisher);
        EditText isbn = (EditText)dialog.findViewById(R.id.addbooks_update_Isbn);
        EditText did = (EditText)dialog.findViewById(R.id.addbooks_update_Did);
        EditText rackcode = (EditText)dialog.findViewById(R.id.addbooks_update_rackcode);

        bookname.setText(bookDetails.getBook_name());
        author.setText(bookDetails.getAuthor_name());
        publisher.setText(bookDetails.getPublisher_name());
        isbn.setText(bookDetails.getI_sbn());
        did.setText(bookDetails.getD_id());
        rackcode.setText(bookDetails.getRack_code());

        // Note:- user cann't change the account number, because i am saving books in the library on the basis of book account number

        Button updatebutton = (Button)dialog.findViewById(R.id.addbooks_update_Button);
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // dismissing the popup dialog on click of update button

                String bkn=bookname.getText().toString();
                String bkan=author.getText().toString();
                String bkpn=publisher.getText().toString();
                //String bkaccn=account.getText().toString();
                String bkisbn=isbn.getText().toString();
                String bkdid=did.getText().toString();
                String bkrc=rackcode.getText().toString();

                //String acc, String isbn ,String bookname, String author, String publ, String  did, String rackcode
                BookDetails bookDetails1 = new BookDetails(bookDetails.getAcc_n(),bkisbn,bkn,bkan,bkpn,bkdid,bkrc);

                // now pointing interface for updation
                updateBookDetails.updatebook(bookDetails1);



            }
        });

    }

    private void showDialogForDelete(BookDetails bookDetails1) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_books);
        dialog.show();

        TextView tvcancel = (TextView)dialog.findViewById(R.id.alert_Cancel_No);
        TextView tvconfirm = (TextView)dialog.findViewById(R.id.alert_Delete_Yes);

        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateBookDetails.deletebook(bookDetails1);
            }
        });

    }

}
