import java.util.Arrays;
import java.util.List;


public class View {
    static class Menu {
        static List<String> menu = Arrays.asList( "### Main menu:",
            "List of products in the Lottery",
            "Save game information",
            "Add a products to the Lottery",
            "Remove products from Lottery",
            "Change the chance of receiving a products",
            "Start the Lottery (if there are < 1000 games, then something may remain...)"
        );


        public static void Show() {
            System.out.println("\n" + menu.get( 0 ) );
            for ( int i = 1; i < menu.size(); i++ ) {
                System.out.println( ( i ) + ". " + menu.get( i ) );
            }
        }

        public static String getMenuItem( int id ) {
            return menu.get( id );
        }

        public static int Size() {
            return menu.size();
        }
    }


    public static String Line() {
        return String.format( "%s", "-".repeat( 83 ) );
    }


    public static void LotterryListShow( String LotterryList ) {
        System.out.println( LotterryList );
        System.out.println( Line() );
    }
}