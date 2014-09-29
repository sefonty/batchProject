package utdallas.cs5348.batchProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Element;

public class PipeCmdCommand extends Command
{
	private String id, parentID, path, inID, outID;
	private List<String> cmdArgs;
	private FileInputStream fis;
	
	@Override
	public String describe()
	{
		if (id != null)
			return "PipeCmdCommand executing: id=" + id + " path=" + path +
					" in=" + inID + " out=" + outID;
		else
			return "Warning: PipeCmdCommand: no cammand to execute";
	}

	@Override
	public void execute(String workingDir, Batch b) throws IOException, ProcessException
	{
		// execute PIPE CMD command here (reference example CmdProcessBuilderStreams.java)
		List<String> command = new ArrayList<String>();
		command.add(path);
		for (String s : cmdArgs)
		{
			command.add(s);
		}
		
		System.out.println("\tPIPE COMMAND LIST:");
		for (String s : command)
		{
			System.out.println("\t"+ s);
		}

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(new File(workingDir));
		builder.redirectError(new File(workingDir, "error.txt"));

		//final Process process = builder.start();
		PipeCommand pc = (PipeCommand)b.getCommands().get(parentID);
		
		if (!(inID == null || inID.isEmpty()))
		{
			pc.process1 = builder.start();
			//OutputStream os = process.getOutputStream();
			OutputStream os = pc.process1.getOutputStream();
			// determine actual file name needed for input:		
			String inFileName = b.getCommands().get(inID).getPath();
			fis = new FileInputStream(new File(workingDir, inFileName));
			int achar;
			while ((achar = fis.read()) != -1)
				os.write(achar);
			os.close();
		}
		
		// for batch5 FILE commands, check element IDs if present for OUT:
		if (b.getCommands().size() > 0)
		{	
			if (!(outID == null || outID.isEmpty()))
			{
				boolean foundMatchingID = this.findMatchingID(b, outID);
				
				// check OUT tag against all existing ID tags
				if (foundMatchingID)
				{	
					pc.process2 = builder.start();
					
					//OutputStream os = process.getOutputStream();
					InputStream is1 = pc.process1.getInputStream();
					OutputStream os2 = pc.process2.getOutputStream();
					// determine actual file name needed for input:		
					int achar;
					while ((achar = is1.read()) != -1)
						os2.write(achar);
					os2.close();
					
					
					// determine actual file name needed for output:
					String outFileName = b.getCommands().get(outID).getPath();
					File outfile = new File(workingDir, outFileName);
					OutputStream fos = new FileOutputStream(outfile);
					InputStream is2 = pc.process2.getInputStream();
					while ((achar = is2.read()) != -1)
					{
						fos.write(achar);
						System.out.print((char) achar);
					}
					fos.close();
				}
				else // did NOT find matching ID when compared to OUT tag
				{
					// throw custom exception here...
					throw new ProcessException("PipeCmdCommand: Unable to locate OUT FileCommand with id " + outID);
				}
			}
		}
	}

	/** 
	 * Parsing for PIPE CMD element:
	 * Save tag values into the appropriate variables
	 */
	@Override
	public void parse(Element element) throws ProcessException
	{
		System.out.println("PipeCmdCommand: parsing element");
		
		// id=
		id = element.getAttribute("id");
		if (id == null || id.isEmpty())
			throw new ProcessException("Missing ID in PipeCmdCommand");
		System.out.println("\tID: " + id);
		
		// parentID=
		Element parentElem = (Element) element.getParentNode();
		parentID = parentElem.getAttribute("id");
		System.out.println("\tparentID: " + parentID);
		
		// path=
		path = element.getAttribute("path");
		if (path == null || path.isEmpty())
			throw new ProcessException("Missing PATH in PipeCmdCommand");
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
