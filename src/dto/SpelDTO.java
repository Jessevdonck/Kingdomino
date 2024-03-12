package dto;

import domein.Speler;

import java.util.HashMap;

public record SpelDTO(HashMap<Kleur, Speler> gekozenSpelers, int dominotegels) {

}
