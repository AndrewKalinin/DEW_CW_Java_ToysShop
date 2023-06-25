public class Product {
    private final int    id;
    private final String name;
    private       int    count;
    private       int    chance;


    public Product( int id, String name, int count, int chance ) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.chance = chance;
    }

    // Геттеры
    public int    getId    () { return this.id;        }
    public String getName  () { return this.name;      }
    public int    getCount () { return this.count;     }
    public int    getChance() { return this.chance;  }

    // Сеттеры
    public void setCount ( int count  ) { this.count  = count;  }
    public void setChance( int chance ) { this.chance = chance; }

    @Override
    public String toString() {
        return String.format( "ID: %2d | Name: %-30s | Count: %3d | Chance to win: %2d%%", id, name, count, chance );
    }

}