package DATA;

import java.io.Serializable;

public class BookDetails implements Serializable {

    String acc_n="",I_sbn="",book_name="",author_name="",publisher_name="",d_id="",rack_code="";
    public BookDetails(String acc, String isbn ,String bookname, String author, String publ, String  did, String rackcode)
    {
        this.acc_n = acc;
        this.I_sbn=isbn;
        this.book_name=bookname;
        this.author_name=author;
        this.publisher_name=publ;
        this.d_id=did;
        this.rack_code=rackcode;

    }

    public BookDetails()
    {

    }

    public String getAcc_n() {
        return acc_n;
    }

    public String getI_sbn() {
        return I_sbn;
    }

    public void setI_sbn(String i_sbn) {
        I_sbn = i_sbn;
    }

    public void setAcc_n(String acc_n) {
        this.acc_n = acc_n;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getD_id() {
        return d_id;
    }

    public String getRack_code() {
        return rack_code;
    }

    public void setRack_code(String rack_code) {
        this.rack_code = rack_code;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }
}
