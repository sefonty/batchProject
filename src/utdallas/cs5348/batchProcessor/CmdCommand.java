package utdallas.cs5348.batchProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

public class CmdCommand extends Command
{
	private String id, path, inID, outID;
	private List<String> cmdArgs;

	// used to print a message to the console when the Command is executed
	@Override
	public String describe()
	{
		if (id != null)
			return "CmdCommand executing: id=" + id + " path=" + path +
					" in=" + inID + " out=" + outID;
		else
			return "Warning: CmdCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir) throws IOException, InterruptedException
	{
		// execute CMD command here (reference example CmdProcessBuilderFiles.java)
		List<String> command = new ArrayList<String>();
		command.add(path);
		for (String s : cmdArgs)
		{
			command.add(s);
		}		
		
		if (!(inID.isEmpty() && inID == null))
			command.add(inID);

		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		builder.directory(new File(workingDir));
		builder.redirectError(new File("error.txt"));
		
		if (!(outID.isEmpty() && outID == null))
			builder.redirectOutput(new File(outID));

		Process process;
		process = builder.start();
		process.waitFor();
		
		System.out.println("CmdCommand finished executing");
	}

	/** 
	 * Parsing for CMD element:
	 * Save tag values into the appropriate variables
	 */
	@Override
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

		// args=
		// Arguments must be passed to ProcessBuilder as a list of
		// individual strings. 
		cmdArgs = new ArrayList<String>();
		String arg = element.getAttribute("args");
		if (!(arg == null || arg.isEmpty()))
		{
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens())
			{
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}
		for (String argi: cmdArgs)
			System.out.println("Arg " + argi);

		// in=
		inID = element.getAttribute("in");
		if (!(inID == null || inID.isEmpty()))
			System.out.println("inID: " + inID);

		// out=
		outID = element.getAttribute("out");
		if (!(outID == null || outID.isEmpty()))
			System.out.println("outID: " + outID);		
	}
	
	public String getID()
	{
		return id;
	}
}
