package utdallas.cs5348.batchProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class BatchProcessor
{
	// batches for testing
	public static Batch[] batch = new Batch[5];
	public static Batch inputBatch;
	
	public static final boolean testing = false;
	public static int batchNum = 1;
	public static final String OS = "MAC";
	
	public static void main(String[] args) throws IOException, InterruptedException, ProcessException
	{
		BatchParser myBatchParser = new BatchParser();
		
		if (!testing)
		{
			if (args.length != 1)
			{
				System.err.println("Invalid command, exactly one argument required for BatchProcessor");
				System.exit(1);
			}
			
			//System.out.println("args[0]: " + args[0]);
			File f = new File(args[0]);
			if (!f.exists())
			{
			  System.out.println("Invalid argument: " + args[0] + " does not exist " +
					  			 "or using wrong slash (\'/\' or \'\\\')");
			  System.exit(2);
			}
			
			System.out.println("BatchProcessor started");
			printLoadBatch(args[0]);
			inputBatch = myBatchParser.buildBatch(new File(args[0]));
			System.out.println("finished parsing batch: " + args[0]);
			
			printExeBatch(args[0]);
			executeBatch(inputBatch);
			printCompletedBatch(args[0]);
		}
		else
		{
			System.out.println("BatchProcessor started");
			printLoadBatch(batchNum);
			switch (batchNum)
			{
				case 1:
					switch (OS)
					{
						case "DOS":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch1.dos.xml"));
							break;
						case "GNU":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch1.gnu.xml"));
							break;
						case "MAC":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch1.mac.xml"));
							break;
					}
					break;
				case 2:
					switch (OS)
					{
						case "DOS":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch2.dos.xml"));
							break;
						case "GNU":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch2.gnu.xml"));
							break;
						case "MAC":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch2.mac.xml"));
							break;
					}
					break;
				case 3:
					switch (OS)
					{
						case "DOS":
						case "GNU":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch3.xml"));
							break;
						case "MAC":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch3.mac.xml"));
							break;	
					}
					break;
				case 4:
					switch (OS)
					{
						case "DOS":
						case "GNU":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch4.xml"));
							break;
						case "MAC":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch4.mac.xml"));
							break;	
					}
					break;
				case 5:
					switch (OS)
					{
						case "DOS":
						case "GNU":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch5.broken.xml"));
							break;
						case "MAC":
							batch[batchNum - 1] = myBatchParser.buildBatch(new File("work/batch5.broken.mac.xml"));
							break;
					}
					break;
			}
			System.out.println("finished parsing batch" + batchNum);
			
			printExeBatch(batchNum);
			executeBatch(batch[batchNum - 1]);
			printCompletedBatch(batchNum);
		}
	}
	
	public static void executeBatch(Batch batch) throws IOException, InterruptedException, ProcessException
	{	
		Map<String, Command> batchCommands = batch.getCommands();
		
		// using Entry allows iterating through a map structure
		for (Entry<String, Command> entry : batchCommands.entrySet())
		{
			System.out.println(batchCommands.get(entry.getKey()).describe());
			
			// working directory null until executing a WDCommand
			System.out.println("\texecuting map key: " + entry.getKey());
			batchCommands.get(entry.getKey()).execute(batch.getWorkingDir(), batch);
		}
		System.out.println("batch completed execution!");
	}
	
	public static void printLoadBatch(int bNum)
	{
		System.out.println("loading XML file for batch" + bNum);
	}
	
	public static void printExeBatch(int bNum)
	{
		System.out.println("executing batch" + bNum);
	}
	
	public static void printCompletedBatch(int bNum)
	{
		System.out.println("completed batch" + bNum);
	}
	
	public static void printLoadBatch(String bFile)
	{
		System.out.println("loading XML file for batch: " + bFile);
	}
	
	public static void printExeBatch(String bFile)
	{
		System.out.println("executing batch: " + bFile);
	}
	
	public static void printCompletedBatch(String bFile)
	{
		System.out.println("completed batch: " + bFile);
	}
}