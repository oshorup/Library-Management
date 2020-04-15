package com.example.librarymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public BooksAddedAdapter(Context con, ArrayList<BookDetails> list) {
        this.context = con;
        this.booklist = list;
        booklistExample=new ArrayList<>(list);
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

    }

    @Override
    public int getItemCount() {
        return booklist.size();
    }

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
    };
}
