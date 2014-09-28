package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public class PipeCommand extends Command
{
	String id, path;
	
	@Override
	public String describe()
	{
		if (id != null)
			return "PipeCommand executing: id=" + id;
		else
			return "Warning: PipeCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir, Batch b)
	{
		System.out.println("PipeCommand finished executing");
		return;
	}

	@Override
	public void parse(Element element) throws ProcessException
	{
		System.out.println("PipeCommand: parsing element");
		
		// id=
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in PipeCommand");
		System.out.println("ID: " + id);
	}
	
	@Override
	public String getID()
	{
		return id;
	}
	
	@Override
	public String getPath()
	{
		return path;
	}
}
