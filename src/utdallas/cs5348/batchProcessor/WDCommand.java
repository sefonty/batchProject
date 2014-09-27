package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public class WDCommand extends Command
{
	private String id, path, inID, outID;

	@Override
	// used to print a message to the console when the Command is executed
	public String describe()
	{
		if (id != null)
			return "CmdCommand executing: id=" + id + " path=" + path +
					" in=" + inID + " out=" + outID;
		else
			return "Warning: CmdCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir)
	{
		// execute CMD command here (reference example CmdProcessBuilderFiles.java)
	}

	@Override
	// save tag values of the command element into the appropriate variables
	public void parse(Element element) throws ProcessException
	{
		// id=
		System.out.println("CmdCommand: parsing element");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in CMD Command");
		System.out.println("ID: " + id);
		
		// path=
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in CMD Command");
		System.out.println("Path: " + path);
	}
	
	public String getWorkDirPath()
	{
		return path;
	}
}
