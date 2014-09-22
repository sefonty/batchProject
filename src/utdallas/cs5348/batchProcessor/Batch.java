package utdallas.cs5348.batchProcessor;

import java.util.Map;

public class Batch
{
	private String workingDir;
	private Map<String, Command> commands;
	
	public void addCommand(Command command)
	{
		
	}
	
	public String getWorkingDir()
	{
		return workingDir;
	}
	
	public Map<String, Command> getCommands()
	{
		return commands;
	}
}
