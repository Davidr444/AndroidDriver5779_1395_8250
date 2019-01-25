package com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {
    private class ListGroupViewHolder{
        public TextView inputName;
        public TextView inputPhoneNumber;
        public TextView numberOfKm;
    }

    private class ListItemViewHolder{
        public TextView inputSource;
        public TextView inputDestination;
        public TextView inputPrice;

        public Button email;
        public Button call;
        public Button message;
        public Button takeDrive;
    }



    private Context context;
    private List<Drive> driveList;


    ExpandableListAdapter(Context context, List<Drive> dataSource)
    {
        this.context = context;
        this.driveList = dataSource;
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
        viewHolder.numberOfKm.setText(AddressToLocation(drive.getSource()).distanceTo(locationA));


        // Return the completed view to render on screen
        return convertView;
    }




    private Location AddressToLocation(Address address){
        // this function converts address to location.
        Location location = new Location("a");
        location.setLatitude(address.getLatitude());
        location.setLongitude(address.getLongitude());

        return location;
    }








    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
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
