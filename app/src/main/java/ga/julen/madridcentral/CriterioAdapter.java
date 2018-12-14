package ga.julen.madridcentral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CriterioAdapter extends BaseAdapter {

    private ArrayList<Criterio> criterios;
    private Context context;

    public CriterioAdapter(ArrayList<Criterio> criterios, Context context) {
        this.criterios = criterios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return criterios.size();
    }

    @Override
    public Object getItem(int position) {
        return criterios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return criterios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_criterio, null);
        TextView lblNombre = view.findViewById(R.id.lbl_nombre);
        lblNombre.setText(criterios.get(position).getNombre());
        return view;
    }
}
