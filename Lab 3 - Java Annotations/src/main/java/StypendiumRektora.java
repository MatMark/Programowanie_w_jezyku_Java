import javax.validation.constraints.*;
import java.math.BigDecimal;

public class StypendiumRektora {

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

    @DecimalMin(value = "2.0", message = "Średnia nie może być mniejsza niż 2.0")
    @DecimalMax(value = "5.5", message = "Średnia nie może być większa niż 5.5")
    private BigDecimal srednia;

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

    public BigDecimal getSrednia() {
        return srednia;
    }

    public void setSrednia(BigDecimal srednia) {
        this.srednia = srednia;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
