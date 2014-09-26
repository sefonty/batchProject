package utdallas.cs5348.batchProcessor;

import java.util.Map;

public class Batch
{
	private String workingDir;
	private Map<String, Command> commands;
	
	public Batch(String inputWD)
	{
		workingDir = inputWD;
		System.out.println("created new Batch");
	}
	
	public void addCommand(Command command)
	{
		commands.put("key", command);
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
