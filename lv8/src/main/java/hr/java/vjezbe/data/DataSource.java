package hr.java.vjezbe.data;

import hr.java.vjezbe.entitet.*;

import java.util.List;

public interface DataSource {
	List<Profesor> readProfesori();
	void createProfesor(Profesor profesor);
	
	List<Student> readStudenti();
	void createStudent(Student student);
	
	List<Predmet> readPredmeti();
	void createPredmet(Predmet predmet);
	void deletePredmet(Predmet predmet);
	void updatePredmet(Predmet oldValue, Predmet newValue);

	List<Ispit> readIspiti();
	void createIspit(Ispit ispit);

	List<ObrazovnaUstanova> readObrazovneUstanove();
	void createObrazovnaUstanova(ObrazovnaUstanova ustanova);
}
