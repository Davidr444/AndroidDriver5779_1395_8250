package com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.OperationApplicationException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.CurrentLocation;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter{
    private class ViewHolder{
        private FloatingActionButton addContacts;
        private TextView payment, endDrive;
    }
    private Location AddressToLocation(Address address){
        // this function converts address to location.
        Location location = new Location("a");
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());

        return location;
    } // this function converts address to location.

    private static List<Drive> drives;
    private Context context;
    private Driver driver;

    public ListViewAdapter(List<Drive> drives, Context context, Driver driver){
        this.drives = drives;
        this.context = context;
        this.driver = driver;
    }

    @Override
    public int getCount() {
        return drives.size();
    }

    @Override
    public Object getItem(int position) {
        return drives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Drive drive = drives.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.finish_ride_item, null, true);
            viewHolder.endDrive = (TextView) convertView.findViewById(R.id.endDriveInput);
            viewHolder.payment = (TextView) convertView.findViewById(R.id.paymentInput);
            viewHolder.addContacts = (FloatingActionButton) convertView.findViewById(R.id.AddContacts);
            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.endDrive.setText(CurrentLocation.getPlace(AddressToLocation(drive.getDestination()),context));
        float payment = AddressToLocation(drive.getSource()).distanceTo(AddressToLocation(drive.getDestination()));
        payment /= 100;
        int temp = (int) payment;
        payment = (float) (temp) / 10;
        viewHolder.payment.setText(String.valueOf(payment) + context.getString(R.string.coin));

        //region Content Provider
        viewHolder.addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
                int rawContactInsertIndex = ops.size();
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());
                //INSERT NAME
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, drive.getName()) // Name of the person
                        .build());
                //INSERT PHONE MOBILE
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, drive.getPhoneNumber()) // Number of the person
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build()); //
                //INSERT EMAIL
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, drive.geteMailAddress())
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .build()); //
                //INSERT ADDRESS: FULL
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, CurrentLocation.getPlace(AddressToLocation(drive.getSource()),context))
                        .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
                        .build());
                // SAVE CONTACT IN BCR Structure
                Uri newContactUri = null;
                //PUSH EVERYTHING TO CONTACTS
                try {
                    ContentProviderResult[] res = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    if (res != null && res[0] != null) {
                        newContactUri = res[0].uri;
                    } else Toast.makeText(context, "Contact not added.", Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    // error
                    Toast.makeText(context, "Error (1) adding contact.", Toast.LENGTH_LONG).show();
                    newContactUri = null;
                } catch (OperationApplicationException e) {
                    // error
                    Toast.makeText(context, "Error (2) adding contact.", Toast.LENGTH_LONG).show();
                    newContactUri = null;
                }
                Toast.makeText(context, "Contact added to system contacts.", Toast.LENGTH_LONG).show();

                if (newContactUri == null) {
                    Toast.makeText(context, "Error creating contact", Toast.LENGTH_LONG);

                }

            }
        });
        //endregion

        return convertView;
    }
}