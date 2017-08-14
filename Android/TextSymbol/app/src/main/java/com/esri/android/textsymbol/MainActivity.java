package com.esri.android.textsymbol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.TextSymbol;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView)findViewById(R.id.map);
        final String SYMBOL_JSON_PATH = getResources().getString(R.string.symbol_json);

        //Create a string from JSON file
        String symbolData = null;
        try {
             symbolData = jsonFileToString(SYMBOL_JSON_PATH);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "JSON file(s) failed to open", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "JSON file(s) failed to parse to open");
        }

        //Creates a java object from the JSON string
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(symbolData);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Failed to read JSON string", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Failed to read JSON string");
        }

        //construct text symbol
        final TextSymbol textSymbol = new TextSymbol(node);

        //Workaround: get the font size from Java object and set symbol size
        JsonNode fontObj = node.get("font");
        double size = fontObj.get("size").asDouble();
        textSymbol.setSize((float) size);

        //Add a point in Charlotte and set symbol using the TextSymbol created from JSON
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            @Override
            public void onStatusChanged(Object o, STATUS status) {
                Point mapPoint = (Point) GeometryEngine.project(new Point(-80.8431 , 35.2271), SpatialReference.create(4326) , mMapView.getSpatialReference());

                Graphic symbolGraphic = new Graphic(mapPoint, textSymbol);
                GraphicsLayer gLayer = new GraphicsLayer();
                mMapView.addLayer(gLayer);
                gLayer.addGraphic(symbolGraphic);
            }
        });

    }

    /**
     * Returns a String representation of input JSON file
     *
     * @param filename the file in assets folder to convert to String
     * @return String of JSON file
     * @throws IOException if unable to open the file
     */
    private String jsonFileToString(String filename) throws IOException {
        InputStream is = getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String bufferString = new String(buffer);
        return bufferString;
    }
}
