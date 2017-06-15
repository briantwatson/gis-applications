/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.android;

import android.app.Activity;
import android.os.Bundle;

import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.map.FeatureSet;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {

    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonFactory factory = new JsonFactory();
        JsonParser parser;
        String FEATURES_JSON_PATH = getResources().getString(R.string.features_json);
        String LAYER_INFO_PATH = getResources().getString(R.string.layerinfo_json);
        String layerInfoString;
        String featureData;
        ArcGISFeatureLayer featureLayer;

        //create Strings from JSON files
        featureData = jsonFileToString(FEATURES_JSON_PATH);
        layerInfoString = jsonFileToString(LAYER_INFO_PATH);

        try {
            //construct parser
            parser = factory.createJsonParser(featureData);
            parser.nextToken();

            //construct FeatureSet using parser on featureData string
            FeatureSet featureSet = FeatureSet.fromJson(parser, true);

            //construct FeatureLayer options and set mode to snapshot
            ArcGISFeatureLayer.Options layerOptions = new ArcGISFeatureLayer.Options();
            layerOptions.mode = ArcGISFeatureLayer.MODE.SNAPSHOT;

            //construct FeatureLayer with components
            featureLayer = new ArcGISFeatureLayer(layerInfoString, featureSet, layerOptions);

            //construct mapView set opacity on FeatureLayer and add layer to mapView
            mMapView = (MapView) findViewById(R.id.map);
            featureLayer.setOpacity(0.5f);
            mMapView.addLayer(featureLayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a String representation of input JSON file
     *
     * @param filename the file to convert to String
     * @return String of JSON file
     * @throws IOException if unable to open the file
     */
    private String jsonFileToString(String filename) {
        String bufferString = "";
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            bufferString = new String(buffer);
        } catch (IOException e) {
            //do nothing
        }
        return bufferString;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.unpause();
    }
}
