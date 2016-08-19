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

import ar.com.gitmo.androidpruebas.R;
import ar.com.gitmo.androidpruebas.models.Actividad;
import ar.com.gitmo.androidpruebas.models.Semana;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Semana> mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView calendarWeek;
        public LinearLayout calendarItems;

        public ViewHolder(View v) {
            super(v);
            calendarWeek = (TextView) v.findViewById(R.id.week);
            calendarItems = (LinearLayout) v.findViewById(R.id.items);
            calendarItems.removeAllViews();
        }
    }

    public void add(int position, Semana item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Semana> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Semana semana = mDataset.get(position);
        SimpleDateFormat sdf= new SimpleDateFormat("MMM-dd");

        holder.calendarWeek.setText(sdf.format(semana.getFechaDesde()) + " " + sdf.format(semana.getFechaHasta()));
/*        holder.calendarWeek.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });*/

        holder.calendarItems.removeAllViews();
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Iterator<Actividad> iterator=semana.getIterator();
        while(iterator.hasNext()){
            Actividad actividad = iterator.next();

            View child = li.inflate(R.layout.calendar_activity, null);
            TextView textView = (TextView)child.findViewById(R.id.activity_item);
            textView.setText(actividad.getNombre());
            holder.calendarItems.addView(child);
        }

        //holder.calendarItems
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}