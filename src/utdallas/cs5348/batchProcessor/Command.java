package utdallas.cs5348.batchProcessor;

import org.w3c.dom.Element;

public abstract class Command
{
	public abstract String describe();
	public abstract void execute(String workingDir);
	public abstract void parse(Element element) throws ProcessException;
}
