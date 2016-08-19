package ar.com.gitmo.androidpruebas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMenuGrid extends BaseAdapter {
    private Context mContext;
    private final String[] nombres;
    private final int[] Imageid;

    public CustomMenuGrid(Context c, String[] nombres, int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.nombres = nombres;
    }

    @Override
    public int getCount() {
        return nombres.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

            // Color del item habilitado
            if (position == 0){
                grid.setBackgroundColor(mContext.getResources().getColor(R.color.itemBackgroundEnabled));
            }

            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            textView.setText(nombres[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}