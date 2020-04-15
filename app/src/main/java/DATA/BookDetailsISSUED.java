package DATA;

import java.io.Serializable;

public class BookDetailsISSUED implements Serializable {


    String acc_nISD="",I_sbnISD="",book_nameISD="",author_nameISD="",publisher_nameISD="",d_idISD="",borrower_ID_ISD="",keyISD="";

    public BookDetailsISSUED(String acc_nISD,String isbn,String bookname , String author,String publ,String did,String borrowerId,String key) {
        this.acc_nISD = acc_nISD;
        this.I_sbnISD=isbn;
        this.book_nameISD=bookname;
        this.author_nameISD=author;
        this.publisher_nameISD=publ;
        this.d_idISD=did;
        this.borrower_ID_ISD=borrowerId;
        this.keyISD=key;
    }

    public BookDetailsISSUED() {

    }

    public String getAcc_nISD() {
        return acc_nISD;
    }

    public String getBook_nameISD() {
        return book_nameISD;
    }

    public void setBook_nameISD(String book_nameISD) {
        this.book_nameISD = book_nameISD;
    }

    public void setAcc_nISD(String acc_nISD) {
        this.acc_nISD = acc_nISD;
    }

    public String getAuthor_nameISD() {
        return author_nameISD;
    }

    public void setAuthor_nameISD(String author_nameISD) {
        this.author_nameISD = author_nameISD;
    }

    public  String getBorrower_ID_ISD() {
        return borrower_ID_ISD;
    }

    public void setBorrower_ID_ISD(String borrower_ID_ISD) {
        this.borrower_ID_ISD = borrower_ID_ISD;
    }

    public String getD_idISD() {
        return d_idISD;
    }

    public String getI_sbnISD() {
        return I_sbnISD;
    }

    public void setI_sbnISD(String i_sbnISD) {
        I_sbnISD = i_sbnISD;
    }

    public String getKeyISD() {
        return keyISD;
    }

    public void setKeyISD(String keyISD) {
        this.keyISD = keyISD;
    }

    public void setD_idISD(String d_idISD) {
        this.d_idISD = d_idISD;
    }

    public String getPublisher_nameISD() {
        return publisher_nameISD;
    }

    public void setPublisher_nameISD(String publisher_nameISD) {
        this.publisher_nameISD = publisher_nameISD;
    }
}
