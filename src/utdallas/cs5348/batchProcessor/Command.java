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
}
