/**
 * Sample application illustrating retrieving a URL parameter, querying FeatureLayer, zooming
 * to feature, and creating a highlight graphic using the Esri JavaScript 4.x API
 *
 * @author Brian Watson
 *
 **/
require([
    "esri/core/urlUtils",
    "esri/symbols/SimpleFillSymbol",
    "esri/Graphic",

    "esri/Map",
    "esri/views/MapView",

    "esri/layers/FeatureLayer",

    "dojo/domReady!"
  ],
  function(
    urlUtils,
    SimpleFillSymbol,
    Graphic,
    Map, MapView,
    FeatureLayer
  ) {

    var map = new Map({
      basemap: "dark-gray-vector"
    });

    var view = new MapView({
      container: "viewDiv",
      map: map,
      center: [-97, 38],
      zoom: 3
    });


    var featureLayer = new FeatureLayer({
      url: "http://sampleserver6.arcgisonline.com/arcgis/rest/services/Census/MapServer/3"
    });
    map.add(featureLayer);



    view.then(getUrlParam)
      .then(queryFeatures)
      .then(zoomTo)
      .then(highlight);

      /**
       * getUrlParam - returns the URL parameter for "STATEFIPS", if no url parameter or not
       * "STATEFIPS", return an error
       *
       * @return {string} - the state FIPS code
       **/
      function getUrlParam() {
      var url = urlUtils.urlToObject(document.URL);
      console.log(url);

      if (!url.query) {
        alert("Please enter a 'STATEFIPS' URL parameter, e.g. 'STATEFIPS=15'");
      } else if (!url.query.STATEFIPS) {
        alert("Enter a valid URL Parameter, e.g. 'STATEFIPS=15'");
      } else {
        return url.query.STATEFIPS;
      }

    }

    /**
     * queryFeatures - Returns a feature from Census FeatureLayer with a given two-digit State FIPS code
     *
     * @param {string} stateFipsCode - The state FIPS code
     * @return {Promise} The FeatureLayer query results for the given FIPS code
     **/
    function queryFeatures(stateFipsCode) {
      //first make sure featureLayer is loaded
      return featureLayer.then(function() {
        var query = featureLayer.createQuery();  //get query from featureLayer definition
        query.where = "STATE_FIPS = " + "'" + stateFipsCode + "'"; //construct WHERE clause
        query.outSpatialReference = view.spatialReference;
        return featureLayer.queryFeatures(query);
      });
    }

    /**
     * zoomTo - zooms the MapView to the geometry of the first feature in the given query response
     *
     * @param {Promise} response - the query response
     * @return {Feature} the first feature in the given query response
     **/
    function zoomTo(response) {
      console.log("query response", response);
      var state = response.features[0];
      view.goTo(state);
      return (state);
    }

    /**
     * highlight - Creates a graphic of the given feature geometry and adds it to the view
     *
     * @param {Feature} feature - the feature to highlight
     **/
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

  });
