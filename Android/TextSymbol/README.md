# TextSymbol from JSON

The following a small sample that illustrates creating a TextSymbol from a JSON file asset.  Currently in Esri 10.2.9 Android API, there is a bug regarding the font size not being honored when initializing a TextSymbol from JSON.  This sample illustrates how to work around this.

## Details
The Android API offers a constructor to instantiate a TextSybmol from a JsonNode: [public TextSymbol (JsonNode node)](https://developers.arcgis.com/android/10-2/api-reference/reference/com/esri/core/symbol/TextSymbol.html#TextSymbol(org.codehaus.jackson.JsonNode))


Using the Jackson ObjectMapper [readTree](https://fasterxml.github.io/jackson-databind/javadoc/2.8/com/fasterxml/jackson/databind/ObjectMapper.html#readTree(java.lang.String)) method, we can return a JsonNode for use with the TextSymbol.  The readTree method is overloaded with several input options. In this sample, we use a JSON string.

```java
String symbolData = null;
symbolData = jsonFileToString(SYMBOL_JSON_PATH);  //see sample for helper method
...
JsonNode node = null;
node = mapper.readTree(symbolData);
```

The JsonNode is then enough to successfully construct a TextSymbol.  However, due to a bug, we will need to use the JsonNode object to retrieve the font size and set the property for the TextSymbol:

```java
JsonNode fontObj = node.get("font");
double size = fontObj.get("size").asDouble();
textSymbol.setSize((float) size);
```
