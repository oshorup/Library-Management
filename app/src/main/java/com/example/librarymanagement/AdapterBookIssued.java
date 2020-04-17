package com.example.librarymanagement;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import DATA.BookDetails;
import DATA.BookDetailsISSUED;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterBookIssued extends RecyclerView.Adapter<AdapterBookIssued.HolderBookIssued>{

    Context contextt;
    ArrayList<BookDetailsISSUED> booklistIssued = new ArrayList<>();

    UpdateBookDetailsIssued updateBookDetailsIssued;

    public AdapterBookIssued(Context conn, ArrayList<BookDetailsISSUED> listt) {
        this.contextt = conn;
        this.booklistIssued = listt;

        updateBookDetailsIssued = (UpdateBookDetailsIssued)contextt;  //initialising the updateBookDetailsIssued parameter,
                                                                      // otherwise we will get null pointer exception
    }


    @NonNull
    @Override
    public HolderBookIssued onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewobjectt = LayoutInflater.from(contextt).inflate(R.layout.recycler_all_books_issued, parent, false);
        HolderBookIssued holderBookIssued = new HolderBookIssued(viewobjectt);
        return holderBookIssued;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBookIssued holder, int position) {

        BookDetailsISSUED bookDetailsIssued = booklistIssued.get(position);

        String booknameISD = bookDetailsIssued.getBook_nameISD();
        String authorNameISD = bookDetailsIssued.getAuthor_nameISD();
        String publisherISD = bookDetailsIssued.getPublisher_nameISD();
        String accountnumberISD = bookDetailsIssued.getAcc_nISD();
        String isbnISD = bookDetailsIssued.getI_sbnISD();
        String didISD = bookDetailsIssued.getD_idISD();
        String borrowerISD = bookDetailsIssued.getBorrower_ID_ISD();

        holder.rowBooknameIssued.setText(booknameISD);
        holder.rowAuthornameIssued.setText(authorNameISD);
        holder.rowPublisherIssued.setText(publisherISD);
        holder.rowAccountnumberIssued.setText(accountnumberISD);
        holder.rowIsbnIssued.setText(isbnISD);
        holder.rowDIdIssued.setText(didISD);
        holder.rowBorrowerIssued.setText(borrowerISD);

        holder.imageRowDeleteIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsISSUED bookDetailsISSUED1 = booklistIssued.get(position);
                showAlertDialogfordeleteIssuedBooks(bookDetailsISSUED1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return booklistIssued.size();
    }

    // below function is useless for now, i will use it in future
    @OnClick({R.id.image_row_edit_issued, R.id.image_row_delete_issued})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_row_edit_issued:
                break;
            case R.id.image_row_delete_issued:
                break;
        }
    }


    public class HolderBookIssued extends RecyclerView.ViewHolder {
       @BindView(R.id.row_Bookname_Issued)
       TextView rowBooknameIssued;
       @BindView(R.id.row_authorname_issued)
       TextView rowAuthornameIssued;
       @BindView(R.id.row_Publisher_issued)
       TextView rowPublisherIssued;
       @BindView(R.id.row_accountnumber_issued)
       TextView rowAccountnumberIssued;
       @BindView(R.id.row_Isbn_issued)
       TextView rowIsbnIssued;
       @BindView(R.id.row_D_id_issued)
       TextView rowDIdIssued;
       @BindView(R.id.row_borrower_issued)
       TextView rowBorrowerIssued;
       @BindView(R.id.image_row_edit_issued)
       ImageView imageRowEditIssued;
       @BindView(R.id.image_row_delete_issued)
       ImageView imageRowDeleteIssued;

        public HolderBookIssued(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, this.itemView);
        }
    }

   public void showAlertDialogfordeleteIssuedBooks(BookDetailsISSUED bookDetailsISSUED1)
    {
        Dialog dialog = new Dialog(contextt);
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
                updateBookDetailsIssued.deleteIssuedBooks(bookDetailsISSUED1);

            }
        });

    }
}
