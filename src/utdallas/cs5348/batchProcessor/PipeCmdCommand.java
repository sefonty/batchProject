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
	public void execute(String workingDir, Batch b) throws IOException, ProcessException, InterruptedException
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

		PipeCommand pc = (PipeCommand)b.getCommands().get(parentID);
		
		// for batch5 FILE error commands, check element IDs if present for IN:
		if (b.getCommands().size() > 0)
		{
			if (!(inID == null || inID.isEmpty()))
			{
				boolean foundMatchingID = this.findMatchingID(b, inID);
				
				// check IN tag against all existing ID tags
				if (foundMatchingID)
				{
					pc.pipeProcess1 = builder.start();
					OutputStream os = pc.pipeProcess1.getOutputStream();
					// determine actual file name needed for input:		
					String inFileName = b.getCommands().get(inID).getPath();
					fis = new FileInputStream(new File(workingDir, inFileName));
					
					System.out.println("writing to pipeProcess1's ouput stream from " + inFileName);
					
					int achar;
					while ((achar = fis.read()) != -1)
					{
						os.write(achar);
						//System.out.print((char) achar);
					}
					
					os.close();
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
					pc.pipeProcess2 = builder.start();					
					InputStream is1 = pc.pipeProcess1.getInputStream();
					OutputStream os2 = pc.pipeProcess2.getOutputStream();
					
					System.out.println("writing to pipeProcess2's ouput stream from " +
									 "pipeProcess1's input stream");
					
					int achar;
					while ((achar = is1.read()) != -1)
					{
						os2.write(achar);
						//System.out.print((char) achar);
					}
					
					os2.close();
					
					// determine actual file name needed for output:
					String outFileName = b.getCommands().get(outID).getPath();
					File outfile = new File(workingDir, outFileName);
					OutputStream fos = new FileOutputStream(outfile);
					InputStream is2 = pc.pipeProcess2.getInputStream();
					
					System.out.println("writing to "+ outFileName + " from " +
							 "pipeProcess2's input stream");
					
					System.out.print("contents of " + outFileName + ": ");
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
					throw new ProcessException(
							"\nError Processing Batch: " +
							"PipeCmdCommand: Unable to locate OUT FileCommand with id: " + outID);
				}
			}
		}
		System.out.println("PipeCmdCommand finished executing");
	}

	/** 
	 * Parsing for PIPE CMD element:
	 * Save tag values into the appropriate variables
	 */
	@Override
	public void parse(Element element) throws ProcessException
	{
		System.out.println("PipeCmdCommand: parsing element attributes");
		
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
