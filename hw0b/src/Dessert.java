public class Dessert {
    static int numDesserts = 0;
    int flavor;
    int price;
    public Dessert(int flavor, int price){
        Dessert.numDesserts += 1;
        this.flavor = flavor;
        this.price = price;
    }
    public void printDessert(){
        System.out.print(this.flavor + " " + this.price + " " + Dessert.numDesserts);
    }
    public static void main(String[] args){
        System.out.println("I love dessert!");
    }
}
