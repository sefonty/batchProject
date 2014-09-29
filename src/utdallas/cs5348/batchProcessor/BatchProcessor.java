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
		//batch1 = myBatchParser.buildBatch(new File("work/batch1.mac.xml"));
		//batch1 = myBatchParser.buildBatch(new File("work/batch1.gnu.xml"));
		//batch1 = myBatchParser.buildBatch(new File("work/batch1.dos.xml"));
		//System.out.println("finished creating new batch1");
		
		//batch2 = myBatchParser.buildBatch(new File("work/batch2.mac.xml"));
		//batch2 = myBatchParser.buildBatch(new File("work/batch2.gnu.xml"));
		//batch2 = myBatchParser.buildBatch(new File("work/batch2.dos.xml"));
		//System.out.println("finished creating new batch2");
		
		//batch3 = myBatchParser.buildBatch(new File("work/batch3.xml"));
		//batch3 = myBatchParser.buildBatch(new File("work/batch3.mac.xml"));
		//System.out.println("finished creating new batch3");
		
		//batch4 = myBatchParser.buildBatch(new File("work/batch4.xml"));
		batch4 = myBatchParser.buildBatch(new File("work/batch4.mac.xml"));
		System.out.println("finished creating new batch4");
		
		//batch5 = myBatchParser.buildBatch(new File("work/batch5.broken.xml"));
		//batch5 = myBatchParser.buildBatch(new File("work/batch5.broken.mac.xml"));
		//System.out.println("finished creating new batch5");
		
		//System.out.println("executing batch1");
		//executeBatch(batch1);
		//System.out.println("finished executing batch1");
		
		//System.out.println("executing batch2");
		//executeBatch(batch2);
		//System.out.println("finished executing batch2");
		
		//System.out.println("executing batch3");
		//executeBatch(batch3);
		//System.out.println("finished executing batch3");
		
		System.out.println("executing batch4");
		executeBatch(batch4);
		System.out.println("finished executing batch4");
		
		//System.out.println("executing batch5");
		//executeBatch(batch5);
		//System.out.println("finished executing batch5");
		
		//System.out.println("all batches executed!");
	}
	
	public static void executeBatch(Batch batch) throws IOException, InterruptedException, ProcessException
	{	
		Map<String, Command> batchCommands = batch.getCommands();
		
		// using Entry allows iterating through a map structure
		for (Entry<String, Command> entry : batchCommands.entrySet())
		{
			System.out.println(batchCommands.get(entry.getKey()).describe());
			
			// working directory null until executing a WDCommand
			System.out.println("executing key: " + entry.getKey());
			batchCommands.get(entry.getKey()).execute(batch.getWorkingDir(), batch);
		}
		System.out.println("batch completed execution!");
	}
}