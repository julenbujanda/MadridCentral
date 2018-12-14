package ga.julen.madridcentral;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String pagina = "https://www.madrid.es/portales/munimadrid/es/Inicio/Movilidad-y-transportes/Incidencias-de-Trafico/Criterios-de-Acceso-y-Autorizaciones/?vgnextfmt=default&vgnextoid=b22fda4581f64610VgnVCM2000001f4a900aRCRD&vgnextchannel=2e30a90d698b1610VgnVCM1000001d4a900aRCRD&rm=60d75ae1b0f64610VgnVCM1000001d4a900aRCRD#";
    private ArrayList<Criterio> criterios;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        new Datos().execute();
    }

    private class Datos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            criterios = new ArrayList<>();
            try {
                Document document = Jsoup.connect(pagina).get();
                Elements titulos = document.select("a[data-parent=.info-detalles]");
                Elements detalles = document.select("div[class=content-panel-detalle panel-body]");
                for (int i = 0; i < titulos.size(); i++) {
                    String titulo = titulos.get(i).text();
                    StringBuilder detalle = new StringBuilder();
                    for (int j = 0; j < detalles.size(); j++) {
                        detalle.append(detalles.get(j).text());
                    }
                    criterios.add(new Criterio(i, titulo, detalle.toString()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListView listView = findViewById(R.id.list_view);
            final CriterioAdapter criterioAdapter = new CriterioAdapter(criterios, context);
            listView.setAdapter(criterioAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Criterio criterio = criterioAdapter.getItem(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.view_detalle, null);
                    TextView lblDetalle = view.findViewById(R.id.lbl_detalle);
                    lblDetalle.setText(criterio.getDetalle());
                    builder.setTitle(criterio.getNombre())
                            .setView(view)
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
        }

    }

}
