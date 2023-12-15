package com.bytebooks.Models;

public class bookdetailsmodel {

    String bookkaname;
    String authorkaname;
    String pdfurl;

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public bookdetailsmodel(String bookname, String authorname, String pdf) {
        this.bookkaname = bookname;
        this.authorkaname = authorname;
        this.pdfurl = pdf;
    }

    public String getBookkaname() {
        return bookkaname;
    }

    public void setBookkaname(String bookkaname) {
        this.bookkaname = bookkaname;
    }

    public String getAuthorkaname() {
        return authorkaname;
    }

    public void setAuthorkaname(String authorkaname) {
        this.authorkaname = authorkaname;
    }
}
