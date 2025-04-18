package ui;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;


import domein.DomeinController;
import domein.DominoTegel;

import domein.Speler;
import dto.DominoTegelDTO;
import dto.SpelerDTO;
import exceptions.*;

import util.Kleur;

public class SpelApplicatie {

	private final DomeinController dc;
	private Scanner input = new Scanner(System.in);

	public SpelApplicatie() {
		this.dc = new DomeinController();
	}

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

		while (selected < 1 || selected > 3) {
			System.out.println("Foute invoer, probeer opnieuw.");
			selected = input.nextInt();
		}

		switch (selected) {

			case 1:
				registreerSpeler();
			case 2:
				startSpel();
			case 3:
				System.exit(0);
		}
	}

	public void startSpel() {

		// arrays om spelers en kleuren die al gekozen zijn uit de lijst te halen
		// te kiezen spelers en kleuren.
		Kleur[] kleurenArray = Kleur.values();
		Kleur[] bufferKleuren;
		SpelerDTO[] spelers = dc.geefAlleSpelers();
		SpelerDTO[] bufferSpelers;
		int maxAantalSpelers = 4;
		int aantalSpelers = 0;


		for (aantalSpelers = 0; aantalSpelers <= maxAantalSpelers - 1; aantalSpelers++) {
			if (aantalSpelers == 3) {
				System.out.println("Wilt u een 4de speler kiezen of wilt u starten?");
				System.out.println("1 : Ja ik wil een 4de speler toevoegen aan het spel");
				System.out.println("2 : Nee, ik wil het spel nu beginnen");
				int keuze = input.nextInt();
				if (keuze == 2) {
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
					spelers[spelerInt].aantalGespeeld(), kleurenArray[kleurInt - 1]);
			// Kleur verwijderen uit opties door nieuwe array te maken en alle kleuren
			// buiten die dat gekozen is eraan toe te voegen

			bufferSpelers = new SpelerDTO[spelers.length - 1];
			int indexSpelers = 0;
			for (SpelerDTO s : spelers) {
				if (s != spelers[kleurInt - 1]) {
					bufferSpelers[indexSpelers++] = s;
				}
			}
			spelers = bufferSpelers;


			// Kleur verwijderen uit opties door nieuwe array te maken en alle kleuren
			// buiten die dat gekozen is eraan toe te voegen
			bufferKleuren = new Kleur[kleurenArray.length - 1];
			int indexKleur = 0;
			for (Kleur k : kleurenArray) {
				if (k != kleurenArray[kleurInt - 1]) {
					bufferKleuren[indexKleur++] = k;
				}
			}
			kleurenArray = bufferKleuren;
			//aantalSpelers++;
		}

		dc.startSpel();
		dc.koningRondeEenShuffle();
		spelSetupBegin();



		while (dc.isEindeSpel()) {

			speelRonde();
		}

	}

	private void spelSetupBegin(){

		for (Kleur kleur: dc.getVolgordeKoning()) {
			System.out.printf("Kiest een tegel : %s\n", kleur);
			toonTegelLijst(dc.getSpel().geefBeginKolom());
			int keuze = input.nextInt();
			dc.voegKoningAanKaart(kleur, keuze ,0);

		}
		spelSituatie();
	}

	private void kiesTegel() {
		boolean geregistreerd = false;
		System.out.println("Welke tegel wil je nemen?");
		int tegel = input.nextInt();
		// DC Fucntie maken voor het kiezen
		while (tegel < 1 || tegel > 4) {
			System.out.println("Foute invoer, probeer opnieuw.");
			tegel = input.nextInt();
		}
		try{
			dc.kiesTegel(tegel);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			kiesTegel();
		}
	}

	private Speler kleurNaarSpeler(Kleur kleur){

		for (Map.Entry<Speler, Kleur> map : dc.getSpelendeSpelers().entrySet()) {
			if(Objects.equals(map.getValue(), kleur)){
				return map.getKey();
			}
			else throw new IllegalArgumentException("geen speler met die kleur");
		}

		return null;
	}
	private void plaatsTegelInBeurt(Kleur kleur){}
	private void speelBeurt(Kleur kleur) {
		plaatsTegelInBeurt(kleur);



		toonTegelLijst(dc.getSpel().geefEindKolom());
		System.out.printf("Welke tegel wil je nemen %s?", kleur.toString());
		int tegel = input.nextInt();

		while (tegel < 1 || tegel > 4) {
			System.out.println("Foute invoer, probeer opnieuw.");
			tegel = input.nextInt();
		}

		kiesTegel();

		System.out.println("Waar wil je de tegel plaatsen?");
		String waar = input.nextLine();
		System.out.println("In welke richting wil je de tegel plaatsen?");
		String richting = input.nextLine();


		//dc.verplaatsDominoTegel(waar, richting);


		// Het systeem valideert volgens DR_PLAATS_DOMINOTEGEL
		// 8.Het systeem voegt de dominotegel op de juiste plaats en richting toe aan het koninkrijk van de speler
	}

	private void toonTegelLijst(List<DominoTegel> lijst) {
		int count = 0;
		for (DominoTegel tegel : lijst) {
			count++;
			System.out.printf("%d : %s", count, tegel.toString());

		}
	}

	private void VraagKleur(Kleur[] kleurenarray) {
		int i = 0;
		for (Kleur kleur : kleurenarray) {

			System.out.printf("%d : %s \n", i + 1, kleur);
			i++;
		}
	}

	public void geefSpelersAlsKeuze(SpelerDTO[] spelers) {
		for (int i = 0; i <= spelers.length - 1; i++) {

			System.out.printf("%d : %s, %d \t | \t", i + 1, spelers[i].gebruikersnaam(), spelers[i].geboortejaar());
			if (i % 2 == 1) {
				System.out.println();
			}
		}
	}

	public void spelSituatie() {
		HashMap<Speler, Kleur> spelers = dc.getSpelendeSpelers();
		for (Speler speler : spelers.keySet()) {
			System.out.println(speler.getGebruikersnaam() + " speelt met kleur " + spelers.get(speler));
			System.out.printf(dc.getSpel().getTegelGebieden().get(spelers.get(speler)).toString());

		}


		// TODO - Zijn koninkrijk ;Zijn koning op een dominotegel in de startkolom of de eindkolom

		List<DominoTegel> beschikbareTegels = dc.getBeginKolom();
		System.out.println("Beschikbare tegels:");
		for (DominoTegel tegel : beschikbareTegels) {
			System.out.printf("%d\n", tegel.getTegelNummer());
		}

		List<DominoTegel> startkolom = dc.getBeginKolom();
		System.out.println("Startkolom:");
		for (DominoTegel tegel : startkolom) {
			System.out.printf("%s : %s \n", tegel.getLandschapType1().toString(), tegel.getLandschapType2().toString());
		}

		List<DominoTegel> tweedekolom = dc.getEindKolom();
		System.out.println("TweedeKolom:");
		for (DominoTegel tegel : tweedekolom) {
			System.out.printf("%s : %s\n", tegel.getLandschapType1().toString(), tegel.getLandschapType2().toString());
		}

	}

	public void speelRonde() {
		for(Kleur kleur : dc.getVolgordeKoning()) {
			speelBeurt(kleur);
		}
		if (dc.isEindeSpel()) {
			System.out.println("Het spel is afgelopen.");
		}
	}

	public void registreerSpeler() {

		boolean isValidInput = false;

		while (!isValidInput) {

			Scanner scanner = new Scanner(System.in);

			System.out.println("Voer je gebruikersnaam in:");
			String gebruikersnaam = scanner.nextLine();

			System.out.println("Voer je geboortejaar in:");
			String geboortejaarString = scanner.nextLine();

			try {
				if (gebruikersnaam.trim().isBlank()) {
					throw new OntbrekendeGebruikersnaamException();
				}

				if (geboortejaarString.trim().isBlank()) {
					throw new OntbrekendGeboortejaarException();
				}

				int geboortejaar = Integer.parseInt(geboortejaarString);


				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				isValidInput = true;

			} catch (OntbrekendeGebruikersnaamException | OntbrekendGeboortejaarException | NumberFormatException e) {
				System.out.println(e.getMessage());
			} catch (GebruikersnaamInGebruikException | TeJongeGebruikerException | OngeldigeGebruikersnaamException |
					 SpatiesInGebruikersnaamException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
				isValidInput = false;
			}
		}

		hoofdmenu();
	}
}
