package ui;

import java.util.Scanner;

import domein.DomeinController;
import domein.Speler;
import exceptions.GebruikersnaamInGebruikException;

public class SpelApplicatie {

	private final DomeinController dc;
	private Scanner input = new Scanner(System.in);

	public SpelApplicatie(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		String gebruikersnaam;
		int geboortejaar;
		boolean loggedin = false;

		while (!loggedin) {
			System.out.println("Gelieve u te registreren.");
			System.out.print("Gebruikersnaam: ");
			gebruikersnaam = input.next();
			System.out.print("\nGeboortejaar:");
			geboortejaar = input.nextInt();
			System.out.println("\nEven geduld...");
			try {
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				loggedin = true;
			} catch (GebruikersnaamInGebruikException e) {
				System.out.println("Gebruiker bestaat al, probeer opnieuw.");
			}
		}
		
		// TODO Hoofdmenu tonen

	}
}
