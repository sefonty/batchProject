package utdallas.cs5348.batchProcessor;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Uses Java's built-in DOM XML parsing to
 * parse the XML batch files.
 */
public class BatchParser
{
	protected Batch buildBatch(File batchFile)
	{
		Batch newBatch = new Batch();
		
		try
		{	
			FileInputStream fis = new FileInputStream(batchFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);

			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			for (int idx = 0; idx < nodes.getLength(); idx++)
			{
				Node node = nodes.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element elem = (Element) node;
					
					// for batch5, check element IDs if present for OUT:
					if ("cmd".equalsIgnoreCase(elem.getNodeName()) &&
							newBatch.getCommands().size() > 0)
					{
						String tempOut = elem.getAttribute("out");
						Command c = new CmdCommand();
						boolean foundMatchingID = false;
						
						// check OUT tag against all pre-existing ID tags
						for (int cIdx = 0; cIdx < newBatch.getCommands().size(); cIdx++)
						{
							c = newBatch.getCommands().get(0);
							if (((CmdCommand) c).getID().equals(tempOut))
								foundMatchingID = true;
						}
						
						if (foundMatchingID)
						{
							Command newCommand = buildCommand(elem);
							newBatch.addCommand(newCommand);
						}
						else // did NOT find matching ID when compared to OUT tag
						{
							// throw exception here...
							System.out.println("Could not find matching ID for outID: " + tempOut);
						}
					}
					else
					{
						Command newCommand = buildCommand(elem);
						newBatch.addCommand(newCommand);
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return newBatch;
	}
	
	private Command buildCommand(final Element elem) throws ProcessException
	{
		String cmdName = elem.getNodeName();
		Command cmd;
		
		if (cmdName == null)
			throw new ProcessException("unable to parse command from " + elem.getTextContent());
		else if ("wd".equalsIgnoreCase(cmdName))
		{
			System.out.println("Parsing wd");
			cmd = new WDCommand();
			cmd.parse(elem);
		}
		else if ("file".equalsIgnoreCase(cmdName))
		{
			System.out.println("Parsing file");
			cmd = new FileCommand();
			cmd.parse(elem);
		}
		else if ("cmd".equalsIgnoreCase(cmdName))
		{
			System.out.println("Parsing cmd");
			cmd = new CmdCommand();
			cmd.parse(elem);
		}
		else if ("pipe".equalsIgnoreCase(cmdName))
		{
			System.out.println("Parsing pipe");
			cmd = new PipeCommand();
			cmd.parse(elem);
		}
		else
		{
			throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
		}
		
		return cmd;
	}
}
