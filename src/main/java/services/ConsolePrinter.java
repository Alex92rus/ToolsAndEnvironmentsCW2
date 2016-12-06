package services;

/**
 * Created by andreas on 02/12/2016.
 */
public class ConsolePrinter
{
    public static void printError( Exception e )
    {
        System.err.println( "ERROR OCCURRED: " +  e.getMessage() );
    }


    public static void print( String message )
    {
        System.out.println( message );
    }


    public static void print( String message, int intends )
    {
        StringBuffer space = new StringBuffer( "" );
        for ( int index = 0; index < intends; index++ )
        {
            space.append( " " );
        }
        ConsolePrinter.print( space + message );
    }
}
