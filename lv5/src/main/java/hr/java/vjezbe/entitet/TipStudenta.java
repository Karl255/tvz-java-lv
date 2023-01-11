package hr.java.vjezbe.entitet;

public enum TipStudenta {
	IZVANREDAN("izvanredan", 0),
	REDOVAN("redovan", 1);

	private String opis;
	private int redniBrojKodIspisa;

	TipStudenta(String opis, int redniBrojKodIspisa) {
		this.opis = opis;
		this.redniBrojKodIspisa = redniBrojKodIspisa;
	}
	
	public static TipStudenta parse(int i) {
		return switch (i) {
			case 0 -> IZVANREDAN;
			case 1 -> REDOVAN;
			default -> throw new RuntimeException("Unreachable code!");
		};
	}

	public String getOpis() {
		return opis;
	}

	public int getRedniBrojKodIspisa() {
		return redniBrojKodIspisa;
	}
}
