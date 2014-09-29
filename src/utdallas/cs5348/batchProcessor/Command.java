package utdallas.cs5348.batchProcessor;

import java.io.IOException;

import org.w3c.dom.Element;

public abstract class Command
{
	public abstract String describe();
	public abstract void execute(String workingDir, Batch batch) throws IOException, InterruptedException, ProcessException;
	public abstract void parse(Element element) throws ProcessException;
	public abstract String getID();
	public abstract String getPath();
	
	public boolean findMatchingID(Batch b, String key)
	{
		Command cmd = null;
		
		// check key against all existing keys
		if (b.getCommands().containsKey(key))
		{
			cmd = b.getCommands().get(key);
			if (cmd.getClass().equals(FileCommand.class)) // may or may not need this line
				return true;
		}
		
		return false; // did NOT find matching ID when compared to key
	}
}
