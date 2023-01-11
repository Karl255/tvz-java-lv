package hr.java.vjezbe.podaci;

import java.util.List;
import java.util.Map;

public interface Datasource<T, S> {
	void spremiPodatke(List<T> data);
	S dohvatiPodatke();
}
