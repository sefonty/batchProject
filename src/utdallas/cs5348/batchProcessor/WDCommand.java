package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public class WDCommand extends Command
{
	private String id, path, inID, outID;

	// used to print a message to the console when the Command is executed
	@Override
	public String describe()
	{
		if (id != null)
			return "WDCommand executing: id=" + id + " path=" + path +
					" in=" + inID + " out=" + outID;
		else
			return "Warning: WDCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir, Batch b) throws ProcessException
	{
		// only need to set workingDir for batch
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in WD Command");
		else
			b.setWorkingDir(path);
	}

	/** 
	 * Parsing for WD element:
	 * Save tag values into the appropriate variables
	 */
	@Override
	public void parse(Element element) throws ProcessException
	{
		// id=
		System.out.println("WDCommand: parsing element");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in WD Command");
		System.out.println("ID: " + id);
		
		// path=
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in WD Command");
		System.out.println("Path: " + path);
	}
	
	@Override
	public String getID()
	{
		return id;
	}
}
