package ga.julen.madridcentral;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase Principal
 *
 * @author Julen Bujanda
 */
public class MainActivity extends AppCompatActivity {

    // Página de la que se extraerán los datos
    private String pagina;
    private ArrayList<Criterio> criterios;
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        new Datos().execute();
    }

    /**
     * AsyncTask que recupera los datos de la página web del Ayuntamiento de Madrid
     *
     * @see android.os.AsyncTask
     */
    private class Datos extends AsyncTask<Void, Void, Void> {

        /**
         * Crea un ProgressDialog que se mostrará hasta que se carguen por completo
         * los datos
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Madrid Central");
            progressDialog.setMessage("Cargando...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        /**
         * Usa la librería JSoup para extraer los datos de la página del
         * Ayuntamiento de Madrid
         *
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            criterios = new ArrayList<>();
            try {
                Document document = Jsoup.connect(pagina).get();
                Elements titulos = document.select("a[data-parent=.info-detalles]");
                Elements detalles = document.select("div[class=content-panel-detalle panel-body]");
                for (int i = 0; i < titulos.size(); i++) {
                    String titulo = titulos.get(i).text();
                    Elements detalle = detalles.get(i).select("div[class=tiny-text]");
                    // language=HTML
                    String html = "<body style=\"text-align: justify;\">" + detalle.html() + "</body>";
                    criterios.add(new Criterio(i, titulo, html));
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
                    WebView webView = view.findViewById(R.id.web_view);
                    webView.loadData(criterio.getDetalle(), "text/html; charset=utf-8", null);
                    builder.setTitle(criterio.getNombre())
                            .setView(view)
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
            progressDialog.dismiss();
        }

    }

}
