# ClusterLayer Labeling Sample

[Live Sample](https://briantwatson.github.io/gis-applications/JavaScript/clusterlayer-labelling/index.html)

## About this Sample

Clustering was added in the JavaScript 3.22 API for FeatureLayers.  [Esri Sample](https://developers.arcgis.com/javascript/3/jssamples/fl_clustering_basic.html)

Currently, to obtain the number of features within a cluster, the only option is to click each cluster and use a popup. No method currently exists to label the clusters. This is a small sample demonstrating a workaround for labeling clusters.

## Usage

When using clustering on a FeatureLayer, a new child layer is created within the parent FeatureLayer. This child layer gets updated on each extent change making labeling a challenge. This sample uses a series of timeouts to ensure the cluster layer is first properly loaded then finished updating on each extent change before labeling the clusters using TextSymbols.

## How it Works

Enabling clustering is as simple as adding the featureReduction property to the FeatureLayer constructor:

    var layer = new FeatureLayer(serviceUrl, {
      outFields: ["facname", "proptype", "factype", "address"],
      featureReduction: {
        type: "cluster"
      }
    });

The cluster layer is a property, `_childLayer` of the FeatureLayer:

    var clusterLayer = layer._childLayer;

However, the `_childLayer` isn't available immediately. It is a simple matter of waiting for the cluster layer to finish loading and add the desired text symbols using the clusters geometry.  Here we've waited 3 seconds:
```
    var textSymbolLayer = new GraphicsLayer();
    map.addLayer(textSymbolLayer);

    setTimeout(function() {

      var clusterLayer = layer._childLayer;

      for (var i = 0; i < clusterLayer.graphics.length; i++) {
        var thisGraphic = clusterLayer.graphics[i];

        var clusterTextSymbol = new TextSymbol();
        ...
        //creating text tymbol
        ...

        var textGraphic = new Graphic(thisGraphic.geometry, clusterTextSymbol);
        clusterTextSymbol.setText(thisGraphic.attributes.cluster_count);
        textSymbolLayer.add(textGraphic);

      }
      textSymbolLayer.redraw();
    }, 3000);
  ```  

The `_childLayer` then is updated on each extent change and new symbols need to be drawn. Here we redraw all text symbols after half a second using the Map objects extent-change event handler.

    map.on("extent-change", function() {
      setTimeout(function() {
        textSymbolLayer.clear();

        ...
        //creating TextSymbols and adding to GraphicsLayer
        ...

      }
      textSymbolLayer.redraw();
    }, 500);
