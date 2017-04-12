# SmartMapping Renderers with Sliders

The smartMapping class provides a collection of helper functions used to create pre-configured renderers for smart feature styling.  The following suite of samples highlights setting up slider widgets with respective renderers returned by the smartMapping helper functions.  Using slider widgets to change these pre-configured renderers is straight-forward for some renderers and not as straight-forward for others.

For more information regarding the smartMapping module in Esri's JavaScript API documentation:  
[esri/renderers/smartMapping](https://developers.arcgis.com/javascript/3/jsapi/esri.renderers.smartmapping-amd.html)

## 'Info' renderers
The SizeInfoSlider and ColorInfoSlider `handle-value-change` events return sizeInfo and colorInfo objects respectively.  Those returned objects can simply be applied to a the renderers created from `smartMapping.createSizeInfo()` and `smartMapping.createColorInfo()` by using the `setVisualVariables()` method on the renderer

## Heatmap renderer
The Heatmap renderer returned by `smartMapping.createHeatmapRenderer()` can be modified with the HeatMapSlider by using the colorStops returned from the `handle-value-change` event.  The Heatmap renderer provides a `setColorStops()` method this the colorStops object can be passed in easily.


## 'Classed' renderers
The 'Classed' renderers created from `smartMapping.createClassedColorRenderer()` and `smartMapping.createClassedSizeRenderer()` with their respective slider widgets require a bit more work to get working together.

The ClassedColorSlider and ClassSizeSlider `handle-value-change` events return breakInfos objects.  Applying these event objects requires several steps, namely the using the `clearBreaks()` and `addBreak()` methods.

However, the symbology will need to be taken from the renderer breaks prior to `clearBreaks()`, as symbology is lost with that method.
