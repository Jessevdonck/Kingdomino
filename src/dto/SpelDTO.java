package dto;

import domein.Speler;
import util.Kleur;

import java.util.HashMap;


public record SpelDTO(HashMap<Kleur, Speler> gekozenSpelers, int dominotegels) {

}
