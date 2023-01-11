package hr.java.vjezbe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Automobil implements Otkljucavanje, TemperaturometarMotora {
	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);

	private final String kljuc;
	private int temperaturaMotora;
	private BlackBox blackBox = new TvzBlackBox();

	public Automobil(String kljuc) {
		this.kljuc = kljuc;
		this.temperaturaMotora = new Random().nextInt(80, 120);
	}

	public String getKljuc() {
		return kljuc;
	}

	public BlackBox getBlackBox() {
		return blackBox;
	}

	public void setBlackBox(BlackBox blackBox) {
		this.blackBox = blackBox;
	}

	@Override
	public void otkljucaj(String kljuc) throws NePodudaraSeKljucException {
		String m = String.format("Pokušaj otključavanja automobila s ključem %s koristeći ključ %s.", this.kljuc, kljuc);
		blackBox.zapisiDogadjaj(m);
		logger.info(m);

		if (this.kljuc.equals(kljuc)) {
			String m2 = "Uspješno otključan automobil!";
			blackBox.zapisiDogadjaj(m2);
			logger.info(m2);
		} else {
			String m2 = String.format("Ključ %s ne odgovara automobilu!", kljuc);
			logger.error(m2);
			throw new NePodudaraSeKljucException(m2);
		}
	}

	@Override
	public int trenutacnaTemperatura() throws PrevisokaTemperaturaException {
		String m = "Mjerenje temperature motora";
		blackBox.zapisiDogadjaj(m);
		logger.info(m);
		
		if (temperaturaMotora < 100) {
			String m2 = String.format("Normalna tTemperatura motora (%d°C).", temperaturaMotora);
			blackBox.zapisiDogadjaj(m2);
			logger.info(m2);
			return temperaturaMotora;
		} else {
			String m2 = String.format("Motor je prekuhao (%d°C)!", temperaturaMotora);
			blackBox.zapisiDogadjaj(m2);
			logger.error(m2);
			throw new PrevisokaTemperaturaException(m2);
		}
	}
}
