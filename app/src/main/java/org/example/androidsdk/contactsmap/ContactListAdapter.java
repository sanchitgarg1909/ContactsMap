package org.example.androidsdk.contactsmap;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {

    private ArrayList<Contact> contactList;
    Context context;

    public ContactListAdapter(Context context, ArrayList<Contact> contactList) {
        this.contactList = contactList;
        this.context = context;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_name,tv_email,tv_phone,tv_officephone;

        public ContactsViewHolder(View convertView) {
            super(convertView);
            tv_name = (TextView) convertView.findViewById(R.id.name);
            tv_email = (TextView) convertView.findViewById(R.id.email);
            tv_phone = (TextView) convertView.findViewById(R.id.phone);
            tv_officephone = (TextView) convertView.findViewById(R.id.officephone);
        }
    }

    public void update(ArrayList<Contact> arrayList){
        this.contactList=arrayList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder viewHolder, int position) {
        final Contact contact = contactList.get(position);
        //Log.d("adapter2",w.getName());
        viewHolder.tv_name.setText("Name: "+contact.getName());
        viewHolder.tv_email.setText("Email: "+contact.getEmail());
        viewHolder.tv_phone.setText("Phone: "+contact.getPhone());
        viewHolder.tv_officephone.setText("Office Phone: "+contact.getOfficePhone());
//        viewHolder.id = w.getId();
//        Log.d("id",viewHolder.id);
//        Picasso.with(context).load(contact.getThumbnailUrl()).into(viewHolder.thumbNail);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_contacts, viewGroup, false);
//        if(variable.equals("explore")) {
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_vertical, viewGroup, false);
//        }else {
//            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_horizontal, viewGroup, false);
//            final float scale = context.getResources().getDisplayMetrics().density;
//            int width = Utils.getScreenWidth(context)-((int) (25 * scale + 0.5f));
//            int height = (int) (200 * scale + 0.5f);
//            RelativeLayout layout = (RelativeLayout) itemView.findViewById(R.id.layout);
//            layout.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
//        }
        return new ContactsViewHolder(itemView);
    }

}