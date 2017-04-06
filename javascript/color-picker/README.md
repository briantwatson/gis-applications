# ColorPicker Widget Sample

## About this Sample
Currently no samples exist demonstrating usage of the ColorPicker widget in the ArcGIS API for JavaScript 3.  The following sample seeks to fill that gap.

## Usage
This sample builds on the 'Add graphics to a map' sample currently available by allowing users to draw graphics on the maps GraphicsLayer applying the selected color in the color widget to each graphic drawn.

## How it Works
The widget instantiation is straightforward:
```
var colorPicker = new ColorPicker({}, "colorPicker");
colorPicker.startup();

colorPicker.on("error", function(msg) {
  console.log("color picker error:  ", msg);
});
```
Then, when constructing graphics to add to the map, simply apply the ColorPicker 'color' property when creatying a fill symbol:
```
var symbol = new SimpleFillSymbol(
  SimpleFillSymbol.STYLE_SOLID,
  new SimpleLineSymbol(
    SimpleLineSymbol.STYLE_SOLID,
    new Color('#000'), 1),
  colorPicker.color
);
```
