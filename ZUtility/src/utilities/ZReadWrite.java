package utilities;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Utility class for handling reading and writing data.
 * @author Carey Zhang
 */
public class ZReadWrite
{
    public static Scanner reader(String file)
    {
        try
        {
            Scanner scanner = new Scanner(new File(file));
            return scanner;
        }
        catch (Exception e)
        {
            System.err.println("Couldn't create scanner => " + e);
            return null;
        }
    }

    public static PrintStream writer(String file)
    {
        try
        {
            PrintStream printer = new PrintStream(new File(file));
            return printer;
        }
        catch (Exception e)
        {
            System.err.println("Couldn't create scanner => " + e);
            return null;
        }
    }

    public static Scanner reader(File file)
    {
        try
        {
            Scanner scanner = new Scanner(file);
            return scanner;
        }
        catch (Exception e)
        {
            System.err.println("Couldn't create scanner => " + e);
            return null;
        }
    }

    public static PrintStream writer(File file)
    {
        try
        {
            PrintStream printer = new PrintStream(file);
            return printer;
        }
        catch (Exception e)
        {
            System.err.println("Couldn't create scanner => " + e);
            return null;
        }
    }

    public static int readInt(Scanner in, String tag)
    {
        String s = in.nextLine().substring(tag.length()+1).trim();
        try
        {
            return Integer.parseInt(s);
        }
        catch (Exception e)
        {
            System.err.println("No int! =>" + e);
            return 0;
        }
    }

    public static double readDouble(Scanner in, String tag)
    {
        String s = in.nextLine().substring(tag.length()+1).trim();
        try
        {
            return Double.parseDouble(s);
        }
        catch (Exception e)
        {
            System.err.println("No double! =>" + e);
            return 0;
        }
    }

    public static String readString(Scanner in, String tag)
    {
        String s = in.nextLine().substring(tag.length()+1).trim();
        return s;
    }

    public static void writeInt(int i, PrintStream out, String tag)
    {
        write(i, out, tag);
    }

    public static void writeDouble(double d, PrintStream out, String tag)
    {
        write(d, out, tag);
    }

    public static void writeString(String s, PrintStream out, String tag)
    {
        write(s, out, tag);
    }

    private static void write(Object obj, PrintStream out, String tag)
    {
        out.println(tag + ": " + obj.toString());
    }


}
