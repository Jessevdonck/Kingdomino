package domein;

import java.time.LocalDate;
import java.time.Period;

public class Speler 
{
    private String gebruikersnaam;
    private int geboortejaar;
    private int aantalGewonnen, aantalGespeeld;
    

    public Speler(String gebruikersnaam,int  geboortejaar) 
    {
    	this(gebruikersnaam,geboortejaar,0,0);
    }
    
    public Speler(String gebruikersnaam,int  geboortejaar, int aantalGewonnen, int aantalGespeeld) 
    {
    	setGebruikersnaam(gebruikersnaam);
    	setGeboortejaar(geboortejaar);
    	setAantalGewonnen(aantalGewonnen);
    	setAantalGespeeld(aantalGespeeld);
    }
    
    

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.isBlank())
			throw new IllegalArgumentException("Gebruikersnaam mag niet leeg zijn.");
		if (gebruikersnaam.length() < 6)
			throw new IllegalArgumentException("Gebruikersnaam is te kort.");
		this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		Period period = Period.between(LocalDate.of(geboortejaar, 1,1), LocalDate.now());
		if (period.getYears() < 6)
			throw new IllegalArgumentException("Je bent te jong.");
		this.geboortejaar = geboortejaar;
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	private void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	private void setAantalGespeeld(int aantalGespeeld) {
		this.aantalGespeeld = aantalGespeeld;
	}
	public String toString(String gebruikersnaam, int geboortejaar) {
		return String.format("Gebruikersnaam: %s, geboortejaar: %d", gebruikersnaam, geboortejaar);
	}
	public String toString(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		return String.format("Gebruikersnaam: %s, geboortejaar: %d, aantal spellen gewonnen: %d, aantal spellen gespeeld: %d", gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
	}
}
