// import java.util.Arrays;
// import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main( String[] args ) {
        Scanner iScanner = new Scanner( System.in, "CP866" );

        Shop shop = new Shop();
        String filePath = "products.csv";
        shop.addFromFile( filePath );

        int    id     = 0;
        String name   = "";
        int    count  = 0;
        int    chance = 0;

        int intInput = 0;
        int command = 0;
        while (true) {
            View.Menu.Show();
            command = getIntInput( "Enter command number", (View.Menu.Size() - 1), iScanner, "Exit" );

            if ( command != 0 ) {
                System.out.println( "\nYou choosed: " + View.Menu.getMenuItem( command ) );
                System.out.println( View.Line() );
            }
            iScanner.nextLine();

            switch ( command ) {
                case 0:
                    iScanner.close();
                    System.out.println( "\n[+] Exiting the application" );
                    System.exit( 0 );
                    break;

                case 1: // Список разыгрываеммых товаров
                    View.LotterryListShow( shop.inLotterry() );
                    break;

                case 2: // Сохранить в файл
                    shop.saveFromFile( filePath, shop.getProducts() );
                    break;

                case 3: // Добавьте товар в розыгрыш
                    id = shop.getNextID();
                    System.out.print("Enter product name: ");
                    name = iScanner.nextLine();

                    intInput = getIntInput( "Enter the quantity of the product", 10000, iScanner, "Cancel" );
                    if ( intInput != 0 ) {
                        count = intInput;
                    } else { continue; }

                    intInput = getIntInput( "Enter a chance to get a product", 100, iScanner, "Cancel" );
                    if ( intInput != 0 ) {
                        chance = intInput;
                    } else { continue; }

                    shop.add( id, name, count, chance );
                    View.LotterryListShow( shop.inLotterry() );
                    System.out.println( "\n[+] New product successfully added to the lottery" );
                    System.out.println( View.Line() );
                    break;

                case 4: // Удалить товар из розыгрыша
                    View.LotterryListShow( shop.inLotterry() );
                    id = getIntInput( "Enter the ID of the product you want to delete", shop.getProductsCount(), iScanner, "Отмена" );
                    shop.del( id );
                    View.LotterryListShow( shop.inLotterry() );
                    System.out.println( "\n[+] Product ID " + id + " successfully removed from the lottery" );
                    System.out.println( View.Line() );
                    break;

                case 5: // Изменить шанс получения товара
                    View.LotterryListShow( shop.inLotterry() );
                    id = getIntInput( "Enter the product ID to change the chance of receiving", shop.getNextID() - 1, iScanner, "Отмена" );
                    int index = shop.getIndex( id );
                    if ( index != -1 ) {
                        intInput = getIntInput( "Enter the chance to receive the product", 100, iScanner, "Отмена" );
                        if ( intInput != 0 ) {
                            chance = intInput;
                        } else {
                            shop.setZeroChance( index );
                        }
                        shop.updChance( index, chance );
                        System.out.println( "[+] Chance of receiving the product with ID " + id + " has been changed" );
                        System.out.println( View.Line() );
                    } else {
                        System.out.println( "[!] Product ID " + id + " is out of stock" );
                        System.out.println( View.Line() );
                    }

                case 6: //"Начать игру":
                    if ( shop.getProductsCount() == 0 || shop.getLotterryCount() == 0 ) {
                        System.out.println("The list of toys for the lottery is empty!");
                        System.out.println( View.Line() );
                        break;
                    }

                    intInput = getIntInput( "Enter the number of game", Integer.MAX_VALUE, iScanner, "Cancel" );
                    if ( intInput != 0 ) {
                        shop.Lottery( intInput );
                    } else { continue; }
                    break;
            }
        }
    }


    // Проверка числового ввода
    public static int getIntInput( String query, int check, Scanner iScanner, String end ) {
        int intInput = 0;
        String errInput = "";
        boolean validInput = false;
        while ( !validInput ) {
            System.out.println( "0. " + end );
            System.out.print( "\n" + query + ": " );

            if ( iScanner.hasNextInt() ) {
                intInput = iScanner.nextInt();
                validInput = ( intInput >= 0 && intInput <= check );
            } else { errInput = iScanner.next(); }

            if ( !validInput ) {
                System.out.println( "\n" + View.Line() );
                System.out.println( "[!] You entered: " + errInput );
                System.out.println( "[!] Invalid input." );
                System.out.println( View.Line() );
            } else if ( intInput == 0 ) {
                System.out.println( "[!] You canceled an action." );
                System.out.println( View.Line() );
            }
        }
        return intInput;
    }
}
