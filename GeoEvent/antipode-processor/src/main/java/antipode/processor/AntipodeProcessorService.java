package antipode.processor;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.processor.GeoEventProcessor;
import com.esri.ges.processor.GeoEventProcessorServiceBase;

public class AntipodeProcessorService extends GeoEventProcessorServiceBase
{
	public AntipodeProcessorService()
	{
		definition = new AntipodeProcessorDefinition();
	}
	
	@Override
	public GeoEventProcessor create() throws ComponentException
	{
		return new AntipodeProcessor(definition);
	}
}