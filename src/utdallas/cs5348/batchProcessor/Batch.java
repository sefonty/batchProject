package utdallas.cs5348.batchProcessor;

import java.util.Map;

public class Batch
{
	private String workingDir;
	private Map<String, Command> commands;
	
	public Batch()
	{
		System.out.println("created new Batch");
	}
	
	// puts command into Map commands with key set to command's ID tag
	public void addCommand(Command command)
	{
		commands.put(command.getID(), command);
	}
	
	public String getWorkingDir()
	{
		return workingDir;
	}
	
	public void setWorkingDir(String inputWorkingDir)
	{
		workingDir = inputWorkingDir;
	}
	
	public Map<String, Command> getCommands()
	{
		return commands;
	}
}
