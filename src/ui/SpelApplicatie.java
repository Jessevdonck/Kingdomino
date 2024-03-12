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
	
	private void hoofdmenu() {
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
	
	public void startSpel()
	{
		Kleur[] kleurenArray = Kleur.values();
		Kleur[] nieuweArray = kleurenArray;
		SpelerDTO[] spelers = dc.geefAlleSpelers();
		HashMap<Kleur, Speler> gekozenSpelers = new HashMap<Kleur, Speler>();
		int aantalSpelers;
		do {
			aantalSpelers = vraagAantalSpelers();
		} while (aantalSpelers != 3 && aantalSpelers != 4);

		for (int i = 0; i <= aantalSpelers - 1; i++) {
			System.out.printf("Gelieve speler %d te kiezen \n", i + 1);

			geefSpelersAlsKeuze(spelers);

			int spelerInt = input.nextInt();
			System.out.printf("Gelieve Kleur te kiezen \n");
			VraagKleur(kleurenArray);
			int kleurInt = input.nextInt();

			dc.voegSpelerToeAanGekozenSpelers(spelers[spelerInt - 1].gebruikersnaam(),
					spelers[spelerInt].geboortejaar(),
					spelers[spelerInt].aantalGewonnen(),
					spelers[spelerInt].aantalGespeeld(),kleurenArray[kleurInt-1]);
			// Kleur verwijderen uit opties door nieuwe array te maken en alle kleuren
			// buiten die dat gekozen is eraan toe te voegen
			nieuweArray = new Kleur[kleurenArray.length - 1];
			int index = 0;
			for(Kleur k : kleurenArray){
				if(k != kleurenArray[kleurInt - 1]){
					nieuweArray[index++] = k;
				}
			}
			kleurenArray = nieuweArray;
		}

			dc.startSpel();
	}

	private void VraagKleur(Kleur[] kleurenarray){
		int i = 0;
		for(Kleur kleur : kleurenarray) {

			System.out.printf("%d : %s \n", i+1, kleur);
			i++;
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

				System.out.printf("%d : %s, %d   |   ", i+1, spelers[i].gebruikersnaam(), spelers[i].geboortejaar());
				if(i % 2 == 1) {
					System.out.println(); }
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
