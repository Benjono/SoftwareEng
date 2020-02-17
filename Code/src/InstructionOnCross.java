public class InstructionOnCross implements Tile {
    private String name;
    private int position;
    private String[] behavior
    public InstructionOnCross(String name, int position, String[] behavior){
        this.name = name;
        this.position = position;
        this.behavior=behavior;
    }

    public String getName(){
        return name;
    }

    public int getPosition(){
        return position;
    }

    public String[] getBehavior(){
        return behavior;
    }
}
