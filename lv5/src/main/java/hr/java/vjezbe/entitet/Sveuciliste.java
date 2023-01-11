package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste <T extends ObrazovnaUstanova, Z> {
	private Z id;
	private List<T> obrazovneUstanove;

	public Sveuciliste(Z id) {
		this.obrazovneUstanove = new ArrayList<>();
		this.id = id;
	}
	
	public void dodajObrazovnuUstanovu(T o) {
		obrazovneUstanove.add(o);
	}
	
	public T dohvatiObrazovnuUstanovu(int i) {
		return obrazovneUstanove.get(i);
	}

	public List<T> getObrazovneUstanove() {
		return new ArrayList<>(obrazovneUstanove);
	}
}
