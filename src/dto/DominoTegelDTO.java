package dto;

import util.LandschapType;

public record DominoTegelDTO(LandschapType landschapType1, LandschapType landschapType2, int tegelNummer, int kronen1, int kronen2, String kleurVanKoning){
}
