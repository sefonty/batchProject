package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public class PipeCmdCommand extends Command
{
	String id;
	
	@Override
	public String describe()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(String workingDir, Batch b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parse(Element element) throws ProcessException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public String getID()
	{
		return id;
	}
}
