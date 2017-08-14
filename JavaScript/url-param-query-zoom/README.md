# URL Parameter Query, Zoom, and Highlight

This sample shows how to work with URL parameters in the 4.x JavaScript API using [urlUtils](https://developers.arcgis.com/javascript/latest/api-reference/esri-core-urlUtils.html). The application uses a singular URL parameter in the form of `STATEFIPS=XX`, replacing "XX" with the FIPS code of your choice. The sample then queries the feature from the FeatureLayer, zooms the MapView to the feature and adds a highlight graphic to the MapView.

## Live Sample
[URL Parameter Query, Zoom, and Highlight](https://briantwatson.github.io/gis-applications/JavaScript/url-param-query-zoom/index.html?STATEFIPS=15)


## Details

This sample uses promise chaining, calling the following functions to achieve our desired result:

First, the URL parameter is obtained:
```javascript
function getUrlParam() {
  var url = urlUtils.urlToObject(document.URL);
  if (url.query.STATEFIPS) {
    return url.query.STATEFIPS;
  } else {
    alert("Enter a valid URL Parameter, e.g. 'STATEFIPS=15'");
  }
}
```


Then the FeatureLayer is queried returning an array of features. In this case, there should be only one feature in the array returned.
```javascript
function queryFeatures(stateFipsCode) {
  return featureLayer.then(function() {
    var query = featureLayer.createQuery();
    query.where = "STATE_FIPS = " + "'" + stateFipsCode + "'";
    query.outSpatialReference = view.spatialReference;
    return featureLayer.queryFeatures(query);
  });
}
```

We then zoom the MapView to the first feature in the array, returning the feature we zoom to
```javascript
function zoomTo(response) {
  var state = response.features[0];
  view.goTo(state);
  return (state);
}
```

Finally, a new graphic is created from the feature with a teal highlight symbology and is added to the MapViews 'graphics' graphicsLayer
```javascript
function highlight(feature) {

  var highlightSymbol = new SimpleFillSymbol({
    color: [0, 0, 0, 0],
    style: "solid",
    outline: {
      color: [0, 255, 255, 1],
      width: 3
    }
  });

  var highlightGraphic = new Graphic({
    geometry: feature.geometry,
    symbol: highlightSymbol
  });

  view.graphics.add(highlightGraphic);
}
```
