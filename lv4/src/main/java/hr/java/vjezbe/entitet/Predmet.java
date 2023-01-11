package hr.java.vjezbe.entitet;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * Predmet koji se izvodi u tercijarnoj obrazovnoj ustanovi.
 */
public class Predmet {
	private String sifra;
	private String naziv;
	private int brojEctsBodova;
	private Profesor nositelj;
	private List<Student> studenti;
	private Map<Integer, List<Student>> studentiPremaDobi;

	public Predmet(String sifra, String naziv, int brojEctsBodova, Profesor nositelj, List<Student> studenti) {
		this.sifra = sifra;
		this.naziv = naziv;
		this.brojEctsBodova = brojEctsBodova;
		this.nositelj = nositelj;
		this.studenti = studenti;
		
		Map<Integer, List<Student>> premaDobi = new HashMap<>();
		
		for (var s : studenti) {
			int brojGodina = Period.between(s.getDatumRodjenja(), LocalDate.now()).getYears();
			
			if (!premaDobi.containsKey(brojGodina)) {
				premaDobi.put(brojGodina, new ArrayList<>());
			}
			
			premaDobi.get(brojGodina).add(s);
		}
		
		studentiPremaDobi = premaDobi;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getBrojEctsBodova() {
		return brojEctsBodova;
	}

	public void setBrojEctsBodova(int brojEctsBodova) {
		this.brojEctsBodova = brojEctsBodova;
	}

	public Profesor getNositelj() {
		return nositelj;
	}

	public void setNositelj(Profesor nositelj) {
		this.nositelj = nositelj;
	}

	public List<Student> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Student> studenti) {
		this.studenti = studenti;
	}

	public Map<Integer, List<Student>> getStudentiPremaDobi() {
		return studentiPremaDobi;
	}
}
