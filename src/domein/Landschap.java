package domein;

import util.LandschapType;

import java.util.Arrays;

public class Landschap
{
    private int kronen;
    private final LandschapType[] landschapTypes = LandschapType.values();
    private LandschapType type;

    /**
     * @param type LandschapType
     * @param aantalkronen int
     */
    public Landschap(LandschapType type, int aantalkronen){
        if(aantalkronen > 3 | aantalkronen < 0) {
            throw new IllegalArgumentException("aantal kronen moet tussen 0 en 3 zijn");
        }
        this.type = type;
        this.kronen = aantalkronen;

    }

    /**
     * @return LandschapType[]
     */
    public int getAantalKronen(){
        return kronen;
    }

    /**
     * @return LandschapType
     */
    public LandschapType getType()
    {
        return type;
    }

    public String toString(){
        return this.type.toString();
    }





















}
