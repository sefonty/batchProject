package utdallas.cs5348.batchProcessor;

import java.util.Map;

public class Batch
{
	private String workingDir;
	private Map<Integer, Command> commands;
	private int createKeyValue = 0; // working directory (wd) command is always index 0
	
	public Batch()
	{
		System.out.println("created new Batch");
	}
	
	public void addCommand(Command command)
	{
		commands.put(createKeyValue, command);
		createKeyValue++;
	}
	
	public String getWorkingDir()
	{
		return workingDir;
	}
	
	public void setWorkingDir(String inputWorkingDir)
	{
		workingDir = inputWorkingDir;
	}
	
	public Map<Integer, Command> getCommands()
	{
		return commands;
	}
}
