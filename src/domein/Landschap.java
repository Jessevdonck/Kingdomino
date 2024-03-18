package domein;

import util.LandschapType;

import java.util.Arrays;

public class Landschap
{
    private int kronen;
    private final LandschapType[] landschapTypes = LandschapType.values();
    private LandschapType type;

    public Landschap(LandschapType type, int aantalkronen){
        boolean typeMatched = false;

        if(aantalkronen > 3 | aantalkronen < 0) {
            throw new IllegalArgumentException("aantal kronen moet tussen 0 en 3 zijn");
        }
        this.type = type;


        this.kronen = aantalkronen;



    }

    public int getAantalKronen(){
        return kronen;
    }

    public LandschapType getType()
    {
        return type;
    }

    public String toString(){
        return this.type.toString();
    }
}
