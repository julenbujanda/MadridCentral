package ga.julen.madridcentral;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String pagina = "https://www.madrid.es/portales/munimadrid/es/Inicio/Movilidad-y-transportes/Incidencias-de-Trafico/Criterios-de-Acceso-y-Autorizaciones/?vgnextfmt=default&vgnextoid=b22fda4581f64610VgnVCM2000001f4a900aRCRD&vgnextchannel=2e30a90d698b1610VgnVCM1000001d4a900aRCRD&rm=60d75ae1b0f64610VgnVCM1000001d4a900aRCRD#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Datos().execute();
    }

    private class Datos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListView listView = findViewById(R.id.list_view);
        }

    }

}
