import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class UrlopDziekański {

    @Min(value = 220000, message = "Podany indeks nie jest prawidłowy")
    @Max(value = 250000, message = "Podany indeks nie jest prawidłowy")
    private int indeks;

    @NotNull(message = "Pole imie nie może być puste")
    private String name;

    @NotNull(message = "Pole nazwisko nie może być puste")
    private String surname;

    @Min(value = 1, message = "Semestr nie może być mniejszy niż 1")
    @Max(value = 10, message = "Semestr nie może być większy niż 10")
    private int semestr;

    @Min(value = 0, message = "Deficyt nie może być mniejszy od 0")
    @Max(value = 210, message = "Deficyt nie może być większy od 210")
    private int deficyt;

    @NotNull(message = "Pole adres nie może być puste")
    private String adres;

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
