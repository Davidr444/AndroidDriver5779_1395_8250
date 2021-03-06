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
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.CurrentLocation;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FactoryBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.FireBaseBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.backend.IBackend;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {
    public void resetData() {
        driveList = constdriveList;
    }

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
    private List<Drive> constdriveList;
    private Driver driver;
    private Filter distanceFilter;
    private CurrentLocation currentLocation;

    public ExpandableListAdapter(Context context, List<Drive> dataSource, Driver driver){
        this.context = context;
        this.driveList = dataSource;
        this.driver = driver;
        currentLocation = new CurrentLocation(context);
        this.constdriveList = driveList;
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
        float temp = AddressToLocation(drive.getSource()).distanceTo(currentLocation.currentLocation) / 100;
        int t = (int) temp;
        viewHolder.numberOfKm.setText(String.valueOf(t));
        viewHolder.inputPhoneNumber.setText(drive.getPhoneNumber());
        // Return the completed view to render on screen

        return convertView;
    }

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

        viewHolder.inputSource.setText(CurrentLocation.getPlace(AddressToLocation(drive.getSource()),context));
        float temp = new FireBaseBackend().getPrice(drive.getSource(),drive.getDestination());
        int t = (int) temp;
        viewHolder.inputPrice.setText(String.valueOf(t));
        viewHolder.inputDestination.setText(CurrentLocation.getPlace(AddressToLocation(drive.getDestination()),context));

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
        if(distanceFilter == null)
            distanceFilter = new DistanceFilter();
        return distanceFilter;
    }

    private class DistanceFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if ((constraint == null) || (constraint.length() == 0)) {
                // No filter implemented we return all the list
                results.values = constdriveList;
                results.count = constdriveList.size();
            }else if (!Character.isDigit(constraint.charAt(constraint.length()-1))){
                results.values = driveList;
                results.count = driveList.size();
            }
            else {
                // We perform filtering operation
                List<Drive> nRideList = new ArrayList<Drive>();
                for (Drive drive : constdriveList) {
                    float distance = (AddressToLocation(drive.getSource()).distanceTo(currentLocation.currentLocation));
                    distance /= 100;
                    int temp = (int)(distance);
                    distance = (float)(temp) / 10;
                    if (distance <= Float.valueOf(constraint.toString()))
                        nRideList.add(drive);
                }
                results.values = nRideList;
                results.count = nRideList.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            driveList = (List<Drive>) results.values;
            notifyDataSetChanged();
        }
    }
}
