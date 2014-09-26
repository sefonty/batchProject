package utdallas.cs5348.batchProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

public class CmdCommand extends Command
{
	private String id, path, inID, outID;
	private List<String> cmdArgs;

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
		System.out.println(this.describe());
	}

	@Override
	public void parse(Element element) throws ProcessException
	{
		System.out.println("CmdCommand: parsing element");
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in CMD Command");
		System.out.println("ID: " + id);
		
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in CMD Command");
		System.out.println("Path: " + path);

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
		{
			System.out.println("Arg " + argi);
		}

		inID = element.getAttribute("in");
		if (!(inID == null || inID.isEmpty()))
		{
			System.out.println("inID: " + inID);
		}

		outID = element.getAttribute("out");
		if (!(outID == null || outID.isEmpty()))
		{
			System.out.println("outID: " + outID);
		}
	}
}
