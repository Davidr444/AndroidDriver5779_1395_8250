package com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {
    private class ListGroupViewHolder{
        public TextView inputName;
        public TextView inputPhoneNumber;
        public TextView numberOfKm;
    } // this class describes the layoutu of a specific group
    private class ListItemViewHolder{
        public TextView inputSource;
        public TextView inputDestination;
        public TextView inputPrice;

        public Button email;
        public Button call;
        public Button message;
        public Button takeDrive;
    } // this class describes the layoutu of a specific item in the list

    private Context context;
    private List<Drive> driveList;
    private Driver driver;

    ExpandableListAdapter(Context context, List<Drive> dataSource, Driver driver){
        this.context = context;
        this.driveList = dataSource;
        this.driver = driver;
    }

    @Override
    public int getGroupCount() {return driveList.size();}

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {return driveList.get(groupPosition);}

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return driveList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Drive drive = (Drive) getGroup((groupPosition));
        ExpandableListAdapter.ListGroupViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_group, null);

            viewHolder = new ExpandableListAdapter.ListGroupViewHolder();
            viewHolder.inputName = convertView.findViewById(R.id.group_input_name);
            viewHolder.inputPhoneNumber = convertView.findViewById(R.id.group_input_phone);
            viewHolder.numberOfKm = convertView.findViewById(R.id.group_input_distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExpandableListAdapter.ListGroupViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.inputName.setText(drive.getName());
        viewHolder.numberOfKm.setText(Float.toString(AddressToLocation(drive.getSource()).distanceTo(CurrentLocation())));
        viewHolder.inputPhoneNumber.setText(drive.getPhoneNumber());
        // Return the completed view to render on screen
        return convertView;
    }
    private Location CurrentLocation(){// todo: implmemtns this function.
        return null; } // this functiong returns the current location of the phone.
    private Location AddressToLocation(Address address){
        // this function converts address to location.
        Location location = new Location("a");
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());

        return location;
    } // this function converts address to location.

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Drive drive = (Drive) getChild(groupPosition, childPosition);
        ListItemViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ListItemViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, null);

            viewHolder.call = (Button) convertView.findViewById(R.id.buttonCall);
            viewHolder.email = (Button) convertView.findViewById(R.id.buttonEmail);
            viewHolder.message = (Button) convertView.findViewById(R.id.buttonMessage);
            viewHolder.takeDrive = (Button) convertView.findViewById(R.id.buttonTakeDrive);

            viewHolder.inputDestination = (TextView) convertView.findViewById(R.id.group_input_destination);
            viewHolder.inputPrice = (TextView) convertView.findViewById(R.id.group_input_price);
            viewHolder.inputSource = (TextView) convertView.findViewById(R.id.group_input_source);

            convertView.setTag(viewHolder);
        }
        else {viewHolder = (ListItemViewHolder) convertView.getTag();}

        viewHolder.inputSource.setText(drive.getSource().toString());
        viewHolder.inputPrice.setText("look at the todo state!");// todo: how do we calculate the price?
        viewHolder.inputDestination.setText(drive.getDestination().toString());

        viewHolder.takeDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "waze://?ll="+drive.getSource().getLatitude()+", "+drive.getDestination().getLongitude()+"&navigate=yes";
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                IBackend backend = FactoryBackend.getBackend();

                backend.changeStatus(drive, Drive.DriveStatus.IN_PROGRESS);
                drive.setBeginning(Calendar.getInstance().getTime());
                drive.setDriverId(driver.getId());

                new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        FactoryBackend.getBackend().updateDrive(drive);
                        return null;
                    }
                }.execute();// this asyncTask should update the drive with its new data
                Toast.makeText(context, R.string.pass_progress,Toast.LENGTH_LONG).show();
                String smsText = context.getString(R.string.Taxi_On_The_Way);
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + drive.getPhoneNumber()));
                smsIntent.putExtra("sms_body", smsText);
                context.startActivity(smsIntent);
                }
        });

        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + drive.getPhoneNumber()));
                context.startActivity(intent);
            }
        });

        viewHolder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("smsto", drive.getPhoneNumber(), null));
                context.startActivity(intent);
            }
        });

        viewHolder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",drive.geteMailAddress(), null));
                context.startActivity(Intent.createChooser(emailIntent, "choose an email client"));
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
