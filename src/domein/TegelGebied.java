package domein;

public class TegelGebied
{
    private String[] gebied;

    public TegelGebied() {
        this.gebied = maakGebied();
    }

    private String[] maakGebied(){
         String[] grid = new String[25];
        for(int i = 0; i < 25;i++){
            grid[i] = String.format("%d", i + 1);
        }
        return grid;
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        for(int i = 0; i< this.gebied.length ;i++){
            if (i % 5 == 4) {
                string.append(String.format("%s \t%n", this.gebied[i]));
            } else {
                string.append(String.format("%s \t", this.gebied[i]));
            }
        }
        return string.toString();
    }
}
