package utdallas.cs5348.batchProcessor;

import java.io.File;

public class BatchProcessor
{
	public static Batch batch1;
	
	public static void main(String[] args)
	{	
		System.out.println("BatchProcessor started");
		
		BatchParser myBatchParser = new BatchParser();
		batch1 = myBatchParser.buildBatch(new File("work/batch1.dos.xml"));
		
	}
	
	public void executeBatch(Batch batch)
	{	
		System.out.println("Program terminated!");
	}
}