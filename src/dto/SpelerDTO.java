package dto;

import domein.DominoTegel;

import java.util.List;

public record SpelerDTO(String gebruikersnaam, int  geboortejaar, int aantalGewonnen, int aantalGespeeld) {

}
