package domein;

import java.util.List;

public class SpelRepository {
	
	private List<Speler> gekozenSpelers;
	private Spel momenteelSpel;
	// KLEUREN groen, blauw, roos, geel
	
	public SpelRepository() {}
	
	public void voegSpelerToeAanSpel(Speler speler) {
		if(gekozenSpelers.size() >= 3 )
			throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
		if (gekozenSpelers.size() < 2)
			throw new IllegalArgumentException("Je moet minimaal 3 spelers hebben.");
		gekozenSpelers.add(speler);
	}
	
	
	public void startSpel() {
		Spel momenteelspel = new Spel();
	}
	
	
	
}
