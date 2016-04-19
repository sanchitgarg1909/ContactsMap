package org.example.androidsdk.contactsmap;


import java.util.ArrayList;

public class RequestCompleted {
    private ArrayList<Contact> arrayList = new ArrayList<>();
    public RequestCompleted(ArrayList<Contact> arrayList){
        this.arrayList = arrayList;
    }
    public ArrayList<Contact> getArrayList(){
        return arrayList;
    }
}
