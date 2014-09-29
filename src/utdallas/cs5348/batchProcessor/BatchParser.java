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
			parseNodeList(nodes, newBatch);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return newBatch;
	}
	
	private void parseNodeList(NodeList nodeList, Batch b) throws ProcessException
	{
		for (int idx = 0; idx < nodeList.getLength(); idx++)
		{
			Node node = nodeList.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem = (Element) node;
				Command newCommand = buildCommand(elem);
				b.addCommand(newCommand);
				
				if (node.hasChildNodes())
				{
					System.out.println("Parsing child nodes of " + elem.getNodeName());
					NodeList childNodes = node.getChildNodes();
					parseNodeList(childNodes, b);
				}				
			}
		}
	}
	
	private Command buildCommand(Element elem) throws ProcessException
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
			if (elem.getParentNode().getNodeName().equalsIgnoreCase("pipe"))
			{
				System.out.println("Parsing pipe cmd");
				cmd = new PipeCmdCommand();
				cmd.parse(elem);
			}
			else
			{
				System.out.println("Parsing cmd");
				cmd = new CmdCommand();
				cmd.parse(elem);
			}
		}
		else if ("pipe".equalsIgnoreCase(cmdName))
		{
			System.out.println("Parsing pipe");
			cmd = new PipeCommand();
			cmd.parse(elem);
		}
		else
			throw new ProcessException("Unknown command " + cmdName + " from: " + elem.getBaseURI());
		
		return cmd;
	}
}
