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
	public void execute(String workingDir, Batch b) throws IOException, InterruptedException, ProcessException
	{		
		// execute CMD command here (reference example CmdProcessBuilderFiles.java)
		List<String> command = new ArrayList<String>();
		command.add(path);
		for (String s : cmdArgs)
		{
			command.add(s);
		}
		
		System.out.println("\tCOMMAND LIST:");
		for (String s : command)
		{
			System.out.println("\t"+ s);
		}

		ProcessBuilder builder = new ProcessBuilder(command);
		//builder.command(command);
		builder.directory(new File(workingDir));
		builder.redirectError(new File(workingDir, "error.txt"));
		
		// for batch5 FILE error commands, check element IDs if present for IN:
		if (b.getCommands().size() > 0)
		{
			if (!(inID == null || inID.isEmpty()))
			{
				boolean foundMatchingID = this.findMatchingID(b, inID);
				
				// check IN tag against all existing ID tags
				if (foundMatchingID)
				{
					// determine actual file name needed for input:			
					String inFileName = b.getCommands().get(inID).getPath();
					//command.add(inFileName); // NJ: don't put input file name in command array list
					builder.redirectInput(new File(workingDir, inFileName));
					System.out.println("reading from input file " + inFileName);
				}
				else // did NOT find matching ID when compared to IN tag
				{
					// throw custom exception here...
					throw new ProcessException(
							"\nError Processing Batch: " +
							"CmdCommand: Unable to locate IN FileCommand with id: " + inID);
				}
			}
		}
		
		// for batch5 FILE error commands, check element IDs if present for OUT:
		if (b.getCommands().size() > 0)
		{	
			if (!(outID == null || outID.isEmpty()))
			{
				boolean foundMatchingID = this.findMatchingID(b, outID);
				
				// check OUT tag against all existing ID tags
				if (foundMatchingID)
				{
					// determine actual file name needed for output:
					String outFileName = b.getCommands().get(outID).getPath();
					builder.redirectOutput(new File(workingDir, outFileName));
					System.out.println("writing to ouput file " + outFileName);
				}
				else // did NOT find matching ID when compared to OUT tag
				{
					// throw custom exception here...
					throw new ProcessException(
							"\nError Processing Batch: " +
							"CmdCommand: Unable to locate OUT FileCommand with id: " + outID);
				}
			}
		}
		
		final Process process = builder.start();
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
		System.out.println("CmdCommand: parsing element attributes");
		
		// id=
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in CmdCommand");
		System.out.println("\tID: " + id);
		
		// path=
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in CmdCommand");
		System.out.println("\tPath: " + path);

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
			System.out.println("\tArg " + argi);

		// in=
		inID = element.getAttribute("in");
		if (!(inID == null || inID.isEmpty()))
			System.out.println("\tinID: " + inID);

		// out=
		outID = element.getAttribute("out");
		if (!(outID == null || outID.isEmpty()))
			System.out.println("\toutID: " + outID);		
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
