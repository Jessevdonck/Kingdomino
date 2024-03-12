package ui;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import domein.DomeinController;
import domein.Speler;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;
import util.Kleur;

public class SpelApplicatie {

	private final DomeinController dc;
	private Scanner input = new Scanner(System.in);

	public SpelApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		hoofdmenu();
	}
	
	public void hoofdmenu() {
		System.out.println("Kingdomino - G59");
		System.out.println("1. Registreer nieuwe speler");
		System.out.println("2. Start nieuw spel");
		System.out.println("3. Afsluiten");
		int selected = input.nextInt();
		
		while (selected < 1 || selected > 3 ) 
		{
		  System.out.println("Foute invoer, probeer opnieuw.");
		  selected = input.nextInt();
		}
		
		switch(selected) {
		
		case 1: registreerSpeler();
		case 2: startSpel();
		case 3: System.exit(0);
		}
	}
	
	public void startSpel() {

		SpelerDTO[] spelers = dc.geefAlleSpelers();
		HashMap<Kleur, Speler> gekozenSpelers = new HashMap<Kleur, Speler>();
		int aantalSpelers;
		do {
			aantalSpelers = vraagAantalSpelers();
		} while (aantalSpelers != 3 && aantalSpelers != 4);

		for (int i = 0; i <= aantalSpelers-1; i++ ) {
			System.out.printf("Gelieve speler %d te kiezen", i+1);
			geefSpelersAlsKeuze(spelers);

			int spelerInt = input.nextInt();
			dc.voegSpelerToeAanGekozenSpelers(spelers[spelerInt].gebruikersnaam(),
					spelers[spelerInt].geboortejaar(),
					spelers[spelerInt].aantalGewonnen(),
					spelers[spelerInt].aantalGespeeld());
				}
	}
	public int vraagAantalSpelers(){
		System.out.println("Met hoeveel spelers wil je spelen, 3 of 4");
		int aantalSpelers = input.nextInt();
		if(aantalSpelers != 3 && aantalSpelers != 4){
			System.out.println("Aantal Spelers moet 3 of 4 zijn");

			input.nextLine();
		}
		return aantalSpelers;
	}

	public void geefSpelersAlsKeuze(SpelerDTO[] spelers) {
		for (int i = 0; i <= spelers.length - 1; i++) {
			if(dc.) {
				System.out.printf("%d : %s, %d", i, spelers[i].gebruikersnaam(), spelers[i].geboortejaar());
			}
		}
	}
	
	public void registreerSpeler() {
		String gebruikersnaam;
		int geboortejaar;
		boolean registered = false;

		while (!registered) {
			System.out.println("Gelieve u te registreren.");
			System.out.print("Gebruikersnaam: ");
			gebruikersnaam = input.next();
			System.out.print("Geboortejaar:");
			geboortejaar = input.nextInt();
			System.out.println("Even geduld...");
			try {
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				registered = true;
			} catch (GebruikersnaamInGebruikException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} 
		}
		hoofdmenu();
	}
}
