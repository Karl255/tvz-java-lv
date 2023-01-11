package hr.java.vjezbe;

import java.time.LocalDateTime;

public class Dogadjaj {
	private int id;
	private String naziv;
	private LocalDateTime vrijemeNastanka;

	public Dogadjaj(int id, String naziv) {
		this.id = id;
		this.naziv = naziv;
		this.vrijemeNastanka = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public LocalDateTime getVrijemeNastanka() {
		return vrijemeNastanka;
	}

	public void setVrijemeNastanka(LocalDateTime vrijemeNastanka) {
		this.vrijemeNastanka = vrijemeNastanka;
	}
}
