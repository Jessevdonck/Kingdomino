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
		
		// arrays om spelers en kleuren die al gekozen zijn uit de lijst te halen
		// te kiezen spelers en kleuren.
		Kleur[] kleurenArray = Kleur.values();
		Kleur[] bufferKleuren;
		SpelerDTO[] spelers = dc.geefAlleSpelers();
		SpelerDTO[] bufferSpelers;
		int maxAantalSpelers = 4;
		int aantalSpelers = 0;


		for (aantalSpelers = 0; aantalSpelers <= maxAantalSpelers - 1; aantalSpelers++) {
			if(aantalSpelers == 3) {
				System.out.println("Wilt u een 4de speler kiezen of wilt u starten?");
				System.out.println("1 : Ja ik wil een 4de speler toevoegen aan het spel");
				System.out.println("2 : Nee, ik wil het spel nu beginnen");
				int keuze = input.nextInt();
				if(keuze == 2 ){
					break;
				}
			}

			System.out.printf("Gelieve speler %d te kiezen \n", aantalSpelers + 1);

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

			bufferSpelers = new SpelerDTO[spelers.length - 1];
			int indexSpelers = 0;
			for(SpelerDTO s : spelers){
				if(s != spelers[kleurInt - 1]){
					bufferSpelers[indexSpelers++] = s;
				}
			}
			spelers = bufferSpelers;


			// Kleur verwijderen uit opties door nieuwe array te maken en alle kleuren
			// buiten die dat gekozen is eraan toe te voegen
			bufferKleuren = new Kleur[kleurenArray.length - 1];
			int indexKleur = 0;
			for(Kleur k : kleurenArray){
				if(k != kleurenArray[kleurInt - 1]){
					bufferKleuren[indexKleur++] = k;
				}
			}
			kleurenArray = bufferKleuren;
			aantalSpelers++;
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


	public void geefSpelersAlsKeuze(SpelerDTO[] spelers) {
		for (int i = 0; i <= spelers.length - 1; i++) {

				System.out.printf("%d : %s, %d   |   ", i+1, spelers[i].gebruikersnaam(), spelers[i].geboortejaar());
				if(i % 2 == 1) {
					System.out.println(); }
		}
	}

	public void spelSituatie() {
		// TODO - implement SpelApplicatie.spelsituatie

	}

	public void speelRonde() {
		// TODO - implement SpelApplicatie.speelRonde

		if (dc.isEindeSpel()) {
			System.out.println("Het spel is afgelopen.");
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
