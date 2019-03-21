import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class UrlopDziekański{

    @Annotate
    @Min(value = 220000, message = "Podany indeks nie jest prawidłowy")
    @Max(value = 250000, message = "Podany indeks nie jest prawidłowy")
    private int indeks;

    @Annotate
    @NotNull(message = "Pole imie nie może być puste")
    private String imie;

    @Annotate
    @NotNull(message = "Pole nazwisko nie może być puste")
    private String nazwisko;

    @Annotate
    @Min(value = 1, message = "Semestr nie może być mniejszy niż 1")
    @Max(value = 10, message = "Semestr nie może być większy niż 10")
    private int semestr;

    @Annotate
    @Min(value = 0, message = "Deficyt nie może być mniejszy od 0")
    @Max(value = 210, message = "Deficyt nie może być większy od 210")
    private int deficyt;

    @Annotate
    @NotNull(message = "Pole adres nie może być puste")
    private String adres;

    public UrlopDziekański() {
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public int getSemestr() {
        return semestr;
    }

    public void setSemestr(int semestr) {
        this.semestr = semestr;
    }

    public int getDeficyt() {
        return deficyt;
    }

    public void setDeficyt(int deficyt) {
        this.deficyt = deficyt;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "UrlopDziekański{" +
                "indeks=" + indeks +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", semestr=" + semestr +
                ", deficyt=" + deficyt +
                ", adres='" + adres + '\'' +
                '}';
    }
}
