package addLines;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;

public class AddLines
{

	// Read a list of numbers from stdin and write the sum to stdout
	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("Missing Argument(s) for AddLines");
			System.exit(0);
		}
		
		System.out.println("args[0]: " + args[0]);
		System.out.println("args[1]: " + args[1]);
		
		try
		{
			//Reader reader = new InputStreamReader(args[0]);
			Reader reader = new InputStreamReader(System.in);
			BufferedReader breader = new BufferedReader(reader);
			String lineRead;
			int sum = 0;
			//while ((lineRead = breader.readLine()) != null && !lineRead.isEmpty())
			while (breader.ready())
			{
				lineRead = breader.readLine();
				StringTokenizer st = new StringTokenizer(lineRead);
				while (st.hasMoreTokens())
				{
					String nums = st.nextToken();
					try
					{
						int temp = Integer.parseInt(nums);
						sum += temp;
					}
					catch (NumberFormatException ex)
					{
						System.err.println("Ignoring " + nums);
						continue;
					}
				}
			}
			System.out.println("sum: " + sum);
			System.out.flush();
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
