import java.util.Arrays;

public class AA
{
	public static void main(String[] args)
	{
		String w = "  1,,     0.00,~ 24.482,~>-25.75,,     0.00, ";
		String[] g = w.split(",");
		System.out.println(g.length);
		System.out.println(Arrays.asList(g));
	}
}
