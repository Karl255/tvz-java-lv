package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student na tercijarnoj obrazovnoj ustanovi.
 */
public class Student extends Osoba {
	private String jmbag;
	private LocalDate datumRodjenja;
	private TipStudenta tipStudenta;

	public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja, TipStudenta tipStudenta) {
		super(ime, prezime);
		this.jmbag = jmbag;
		this.datumRodjenja = datumRodjenja;
		this.tipStudenta = tipStudenta;
	}

	public String getJmbag() {
		return jmbag;
	}

	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return ime.equals(student.ime) && prezime.equals(student.prezime) && jmbag.equals(student.jmbag) && datumRodjenja.equals(student.datumRodjenja);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ime, prezime, jmbag, datumRodjenja);
	}

	public TipStudenta getTipStudenta() {
		return tipStudenta;
	}

	public void setTipStudenta(TipStudenta tipStudenta) {
		this.tipStudenta = tipStudenta;
	}
}
