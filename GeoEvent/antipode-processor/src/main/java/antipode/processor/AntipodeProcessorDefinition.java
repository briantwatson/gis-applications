package antipode.processor;

import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class AntipodeProcessorDefinition extends GeoEventProcessorDefinitionBase
{
	public AntipodeProcessorDefinition()
	{
		;
	}

	@Override
	public String getName()
	{
		return "AntipodeProcessor";
	}

	@Override
	public String getDomain()
	{
		return "antipode.processor";
	}

	@Override
	public String getVersion()
	{
		return "10.5.0";
	}

	@Override
	public String getLabel()
	{
	  /**
     * Note: by using the ${myBundle-symbolic-name.myProperty} notation, the
     * framework will attempt to replace the string with a localized string in
     * your properties file.
     */
		return "${sample.gep.antipode-processor.PROCESSOR_LABEL}";
	}

	@Override
	public String getDescription()
	{
	  /**
     * Note: by using the ${myBundle-symbolic-name.myProperty} notation, the
     * framework will attempt to replace the string with a localized string in
     * your properties file.
     */
		return "${sample.gep.antipode-processor.PROCESSOR_DESC}";
	}

	@Override
	public String getContactInfo()
	{
		return "yourname@yourcompany.com";
	}
}
