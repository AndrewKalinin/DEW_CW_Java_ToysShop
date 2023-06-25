import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;


public class Shop {
    private final ArrayList<Product> products = new ArrayList<>();

    // Добавление товара
    public void add( Product product ) {
        this.products.add( product );
    }
    public void add( int id, String name, int count, int chance ) {
        this.products.add( new Product( id, name, count, chance ) );
    }


    public Product getProduct( int index ) {
        Product product = products.get( index );
        return product;
    }


    // Установка шанса
    public void updChance( int index, int chance ) {
        this.products.get( index ).setChance( chance );
    }

    // Обнуление шанса
    public void setZeroChance( int index ) {
        this.products.get( index ).setChance( 0 );
    }

    // Удаление товара
    public void del( int id ) {
        int index = getIndex( id );
        if ( index != -1 ) {
            this.products.remove( index );
        } else {
            System.out.println( "[!] Product ID " + id + " is out of lottery" );
            System.out.println( View.Line() );
        }
    }

    // Получение индекса из списка товаров
    public int getIndex( int id ) {
        for ( int i = 0; i < this.products.size(); i++ ) {
            if ( this.products.get( i ).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    // Вывод списка товаров для лотереи
    public String inLotterry() {
        StringBuilder sb = new StringBuilder();
        sb.append( "\n### List of products in the Lottery:\n" );
        for ( Product product : this.products ) {
            sb.append( product );
            sb.append( "\n" );
        }
        return sb.toString();
    }


    public int getLotterryCount() {
        int result = 0;
        for ( int i = 0; i < this.products.size(); i++ ) {
            result += this.products.get( i ).getCount();
        }
        return result;
    }


    public void Lottery( int rounds ) {
        ArrayList<Product> lotProducts = new ArrayList<>();
        int i = 0;
        Random random = new Random();

        int win = 0;
        for ( i = 0; i < this.products.size(); i++ ) {
            if ( this.products.get( i ).getCount() != 0 ) {
                lotProducts.add( this.products.get( i ) );
            }
        }

        int r = 0;
        for ( i = 0; i < rounds; i++ ) {
            if ( lotProducts.size() == 0 ) {
                System.out.println ("\nThe list of toys for the game is empty!" );
                System.out.println( "We apologize and look forward to seeing you next time!" );
                System.out.println( View.Line() );
                break;
            }

            r = random.nextInt( lotProducts.size() );
            System.out.println( "\n" + (i + 1) + ". You have a chance to win " + lotProducts.get( r ) );
            win = lotProducts.get( r ).getChance();
            for ( int j = 0; j < 3; j++ ) {
                win += random.nextInt( 40 );
            }
            if ( win >= 90 ) {
                System.out.println( "Congrats you get: " + lotProducts.get( r ).getName() );
                lotProducts.get( r ).setCount( lotProducts.get( r ).getCount() - 1 );
                if ( lotProducts.get( r ).getCount() <= 0 ) {
                    System.out.println( "Products: " + lotProducts.get( r ).getName() + ", is no more" );
                    lotProducts.remove( r );
                }
            } else {
                System.out.println( "Sorry: " + lotProducts.get( r ).getName() + " you didn't receive" );
            }
        }
        // Запишем, что из игрушек осталось после розыгрыша, если что-то осталось...
        this.saveFromFile( "Remaining_product.csv", lotProducts );
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    // Получить индекс на 1 больше
    public int getNextID() {
        return products.get(products.size() - 1).getId() + 1;
    }

    // Количество товаров в розыгрыше
    public int getProductsCount() {
        return products.size();
    }

    // Чтение из файла
    public void addFromFile( String filePath ) {
        // TO-DO: Проверка - Существует ли файл
        try ( BufferedReader iReader = new BufferedReader( new FileReader( filePath, StandardCharsets.UTF_8 ) ) ) {
            String line;
            // for ( String line = reader.readLine(); line != null; line = reader.readLine() ) {
            while ( ( line = iReader.readLine() ) != null ) {
                String[] rec = line.split( "," );
                int count = Integer.parseInt( rec[2] );
                if ( count > 0 ) {
                    int id     = Integer.parseInt( rec[0] );
                    int chance = Integer.parseInt( rec[3] );
                    // TO-DO: Проверка - Товар уже добавлен, увеличить количество
                    Product product = new Product( id, rec[1], count, chance );
                    products.add( product );
                }
            }
        } catch (IOException e) {
            System.out.println( "\n" + View.Line() );
            System.out.println( "[!] Error reading file" + filePath + ": " + e.getMessage());
            System.out.println( View.Line() );
        }
    }

    // Запись в файл
    public void saveFromFile( String filePath, ArrayList<Product> products ) {
        try ( BufferedWriter iWriter = new BufferedWriter( new FileWriter( filePath, StandardCharsets.UTF_8 ) ) ) {
            // for ( Product product : products ) {
            for ( int i = 0; i < products.size(); i++ ) {
                int    id     = products.get( i ).getId();
                String name   = products.get( i ).getName();
                int    count  = products.get( i ).getCount();
                int    chance = products.get( i ).getChance();
                iWriter.write( String.format( "%d,%s,%d,%s%n", id, name, count, chance ) );
            }

            if ( products.size() == 0 ) {
                System.out.println( "\n[*] Sorry, the Products is out of the game, we need to go for a new batch" );
                System.out.println( View.Line() );
            } else {
                System.out.println( "\n[+] Game info successfully saved" );
                System.out.println( View.Line() );
            }
        } catch ( IOException e ) {
            System.out.println( "\n" + View.Line() );
            System.out.println( "[!] Error writing to file " + filePath + ": " + e.getMessage());
            System.out.println( View.Line() );
        }
    }

}