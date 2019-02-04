/*todo: check if the project works without this file
package com.jct.davidandyair.androiddriver5779_1395_8250.controller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Drive;
import com.jct.davidandyair.androiddriver5779_1395_8250.model.entities.Driver;

import java.util.List;

public class ExpandableListProgressAdapter extends BaseExpandableListAdapter implements Filterable {
    private List<Drive> drives;
    private Context context;
    private Driver driver;

    public ExpandableListProgressAdapter(List<Drive> drives, Context context, Driver driver) {
        this.drives = drives;
        this.context = context;
        this.driver = driver;
    }


    @Override
    public int getGroupCount() {
        return drives.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return drives.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return drives.get(groupPosition);
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
        return null;
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
*/