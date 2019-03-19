import javax.validation.constraints.*;
import java.math.BigDecimal;

public class StypendiumSocjalne {

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

    @DecimalMin(value = "0", message = "Dochód nie może być mniejszy niż 0")
    private BigDecimal dochod;

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

    public BigDecimal getDochod() {
        return dochod;
    }

    public void setDochod(BigDecimal dochod) {
        this.dochod = dochod;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
