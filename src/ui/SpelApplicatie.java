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
		
		registreerSpeler();
		// TODO Hoofdmenu tonen
		
		System.out.println("1. Registreer nieuwe speler");
		System.out.println("2. Start nieuw spel");
		System.out.println("3. Afsluiten");

	}
	
	public void registreerSpeler() {
		String gebruikersnaam;
		int geboortejaar;
		boolean registred = false;

		while (!registred) {
			System.out.println("Gelieve u te registreren.");
			System.out.print("Gebruikersnaam: ");
			gebruikersnaam = input.next();
			System.out.print("\nGeboortejaar:");
			geboortejaar = input.nextInt();
			System.out.println("\nEven geduld...");
			try {
				dc.registreerSpeler(gebruikersnaam, geboortejaar);
				registred = true;
			} catch (GebruikersnaamInGebruikException e) {
				System.out.println(e.getMessage());
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
