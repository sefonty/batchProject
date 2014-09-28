package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public class FileCommand extends Command
{
	private String id, path;

	@Override
	public String describe()
	{
		if (id != null)
			return "FileCommand executing: id=" + id + " path=" + path;
		else
			return "Warning: FileCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir, Batch b)
	{
		System.out.println("FileCommand finished executing");
		return;
	}

	@Override
	public void parse(Element element) throws ProcessException
	{
		System.out.println("FileCommand: parsing element");
		
		// id=
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in FileCommand");
		System.out.println("ID: " + id);
		
		// path=
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in FileCommand");
		System.out.println("Path: " + path);
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
