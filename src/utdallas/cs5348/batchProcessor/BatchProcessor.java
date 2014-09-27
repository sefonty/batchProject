package utdallas.cs5348.batchProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class BatchProcessor
{
	public static Batch batch1, batch2, batch3, batch4, batch5;
	
	public static void main(String[] args) throws IOException, InterruptedException, ProcessException
	{	
		System.out.println("BatchProcessor started");
		
		BatchParser myBatchParser = new BatchParser();
		batch1 = myBatchParser.buildBatch(new File("work/batch1.gnu.xml"));
		//batch1 = myBatchParser.buildBatch(new File("work/batch1.dos.xml"));
		//batch2 = myBatchParser.buildBatch(new File("work/batch2.gnu.xml"));
		//batch2 = myBatchParser.buildBatch(new File("work/batch2.dos.xml"));
		//batch3 = myBatchParser.buildBatch(new File("work/batch3.xml"));
		//batch4 = myBatchParser.buildBatch(new File("work/batch4.xml"));
		//batch5 = myBatchParser.buildBatch(new File("work/batch5.xml"));
		
		executeBatch(batch1);
		//executeBatch(batch2);
		//executeBatch(batch3);
		//executeBatch(batch4);
		//executeBatch(batch5);
		
		System.out.println("all batches executed!");
	}
	
	public static void executeBatch(Batch batch) throws IOException, InterruptedException, ProcessException
	{	
		Map<String, Command> batchCommands = batch.getCommands();
		
		// using Entry allows iterating through a map structure
		for (Entry<String, Command> entry : batchCommands.entrySet())
		{
			batchCommands.get(entry.getKey()).describe();
			batchCommands.get(entry.getKey()).execute(batch.getWorkingDir(), batch);
		}
		System.out.println("batch completed execution!");
	}
}