package domein;

import exceptions.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

public class Speler 
{
    private String gebruikersnaam;
    private int geboortejaar;
    private int aantalGewonnen, aantalGespeeld;

	/**
	 * Constructor voor Speler met gebruikersnaam en geboortejaar
	 * @param gebruikersnaam
	 * @param geboortejaar
	 */
    public Speler(String gebruikersnaam,int  geboortejaar)
    {
    	this(gebruikersnaam,geboortejaar,0,0);
    }

	/**
	 * Constructor voor Speler
	 * @param gebruikersnaam
	 * @param geboortejaar
	 * @param aantalGewonnen
	 * @param aantalGespeeld
	 */
    public Speler(String gebruikersnaam,int  geboortejaar, int aantalGewonnen, int aantalGespeeld)
    {
    	setGebruikersnaam(gebruikersnaam);
    	setGeboortejaar(geboortejaar);
    	setAantalGewonnen(aantalGewonnen);
    	setAantalGespeeld(aantalGespeeld);

    }

	/**
	 * @return gebruikersnaam
	 */
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	/**
	 * @param gebruikersnaam
	 */
	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.isBlank())
			throw new OntbrekendeGebruikersnaamException();
		if (gebruikersnaam.length() < 6)
			throw new OngeldigeGebruikersnaamException();
		this.gebruikersnaam = gebruikersnaam;
	}

	/**
	 * @return geboortejaar
	 */
	public int getGeboortejaar() {
		return geboortejaar;
	}

	/**
	 * @param geboortejaar
	 */
	private void setGeboortejaar(int geboortejaar) {
		String geboortejaarString = Integer.toString(geboortejaar);

		Period period = Period.between(LocalDate.of(geboortejaar, 1,1), LocalDate.now());
		if (geboortejaarString.trim().isBlank())
			throw new OntbrekendeGebruikersnaamException();
		if (period.getYears() < 6)
			throw new TeJongeGebruikerException();


		this.geboortejaar = geboortejaar;
	}

	/**
	 * @return aantal gewonnen spellen
	 */
	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	/**
	 * @param aantalGewonnen
	 */
	public void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	/**
	 * @return aantal gespeelde spellen
	 */
	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	/**
	 * @param aantalGespeeld
	 */
	public void setAantalGespeeld(int aantalGespeeld) {
		this.aantalGespeeld = aantalGespeeld;
	}
	public String toString(String gebruikersnaam, int geboortejaar) {
		return String.format("Gebruikersnaam: %s, geboortejaar: %d", gebruikersnaam, geboortejaar);
	}
	public String toString(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		return String.format("Gebruikersnaam: %s, geboortejaar: %d, aantal spellen gewonnen: %d, aantal spellen gespeeld: %d", gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
	}
}
