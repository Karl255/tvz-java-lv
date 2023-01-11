package hr.java.vjezbe;

import java.util.Arrays;

public class TvzBlackBox implements BlackBox {
	private Dogadjaj[] dogadjaji = new Dogadjaj[0];
	private int nextId = 1;

	@Override
	public Dogadjaj[] getDogadjaji() {
		return dogadjaji;
	}

	@Override
	public void zapisiDogadjaj(String naziv) {
		dogadjaji = Arrays.copyOf(dogadjaji, dogadjaji.length + 1);
		dogadjaji[dogadjaji.length - 1] = new Dogadjaj(nextId++, naziv);
	}

	@Override
	public void obrisiDogadjaje() {
		dogadjaji = new Dogadjaj[0];
	}
}
