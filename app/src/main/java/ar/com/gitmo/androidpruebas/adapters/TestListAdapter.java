package ar.com.gitmo.androidpruebas.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import ar.com.gitmo.androidpruebas.R;
import ar.com.gitmo.androidpruebas.models.Actividad;
import ar.com.gitmo.androidpruebas.models.Semana;
import ar.com.gitmo.androidpruebas.models.Test;

/**
 * Created by gaston.cocce on 25/08/2016.
 */
public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder>{

    private ArrayList<Test> mDataset;

    // Define listener member variable
    private static OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        TestListAdapter.listener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView testName;
        public TextView testDesc;

        public ViewHolder(View view) {
            super(view);
            testName = (TextView) view.findViewById(R.id.test_name);
            testDesc = (TextView) view.findViewById(R.id.test_description);
            view.setOnClickListener(this);
        }

        public void onClick(View view){
            listener.onItemClick(this.getLayoutPosition(), view);
        }
    }

    public void add(int position, Test item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Test item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TestListAdapter(ArrayList<Test> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Test test = mDataset.get(position);

        holder.testName.setText(test.getName());
        holder.testDesc.setText(test.getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
