package antipode.processor;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.core.property.Property;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.processor.GeoEventProcessorBase;
import com.esri.ges.processor.GeoEventProcessorDefinition;

public class AntipodeProcessor extends GeoEventProcessorBase
{

  /**
   * Initialize the i18n Bundle Logger
   * 
   * See {@link BundleLogger} for more info.
   */
  private static final BundleLogger LOGGER    = BundleLoggerFactory.getLogger(AntipodeProcessor.class);

  public AntipodeProcessor(GeoEventProcessorDefinition definition) throws ComponentException
  {
    super(definition);
    geoEventMutator = true;
  }

  @Override
  public GeoEvent process(GeoEvent dataMessage) throws Exception
  {
	    LOGGER.info("PROCESSING_MSG");
	    
	    if (dataMessage.getGeometry() == null)
		      return dataMessage;
	    
	    MapGeometry mg = dataMessage.getGeometry();
	    
	    //checking for non GCS coordinate system so we don't calculate incorreclty
	    SpatialReference sr = mg.getSpatialReference();
	    if (sr.getCoordinateSystemType() != SpatialReference.Type.Geographic){
	    	LOGGER.info("GoeEvent Geometry not a Geographic Coordinate System" );
	    	return dataMessage;
	    }
	    
	    
	    Geometry gm = mg.getGeometry();
	    
	    //check to make sure geometry is point
	    if (gm.getType() != Geometry.Type.Point)
	    	return dataMessage;
	    
	    Point p = (Point) gm;
	    
	    double x = p.getX();
	    double y = p.getY();

	    //flip latitude
	    y *= -1;
	    
	    //flip longitude
	    if (x > 0) {
	    	x -= 180;
	    } else {
	    	x += 180;
	    }
	    
	    //set new x and y and MapGeometry
	    p.setX(x);
	    p.setY(y);
	    mg.setGeometry(p);
	    
	    //set geometry on GeoEvent and return
	    dataMessage.setGeometry(mg);
	    return dataMessage;
  }

  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(definition.getName());
    sb.append("/");
    sb.append(definition.getVersion());
    sb.append("[");
    for (Property p : getProperties())
    {
      sb.append(p.getDefinition().getPropertyName());
      sb.append(":");
      sb.append(p.getValue());
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();
  }
}
