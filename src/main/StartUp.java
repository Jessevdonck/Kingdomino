package main;

import domein.DomeinController;
import domein.TegelGebied;
import ui.SpelApplicatie;

public class StartUp {
	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		new SpelApplicatie(dc).start();
	}
}
