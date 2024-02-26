package domein;

import java.util.HashMap;
import java.util.List;

public class SpelRepository {
	
	private List<Speler> gekozenSpelers;
	
	public SpelRepository() {}
	
	public void voegSpelerToe(Speler speler) {
		if(gekozenSpelers.size() >= 3 )
			throw new IllegalArgumentException("Het maximum aantal spelers is bereikt.");
		if (gekozenSpelers.size() < 2)
			throw new IllegalArgumentException("Je moet minimaal 3 spelers hebben.");
		gekozenSpelers.add(speler);
	}
	
	public void startSpel() {
		
	}
	
	
	
}
