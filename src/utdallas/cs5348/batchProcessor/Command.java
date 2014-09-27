package utdallas.cs5348.batchProcessor;

import java.io.IOException;

import org.w3c.dom.Element;

public abstract class Command
{
	public abstract String describe();
	public abstract void execute(String workingDir) throws IOException, InterruptedException;
	public abstract void parse(Element element) throws ProcessException;
}
