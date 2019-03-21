import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FormularzKontaktowy {
    @Annotate
    @NotNull(message = "Pole imie nie może być puste")
    private String imie;

    @Annotate
    @NotNull(message = "Pole nazwisko nie może być puste")
    private String nazwisko;

    @Annotate
    @NotNull(message = "Pole adres nie może być puste")
    private String adres;

    @Annotate
    @NotNull(message = "Pole email nie może być puste")
    private String email;

    @Annotate
    @NotNull(message = "Pole telefon nie może być puste")
    @Size(min = 9, max = 9,message = "Numer musi się składać dokładnie z 9 cyfr")
    private String telefon;

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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "FormularzKontaktowy{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", adres='" + adres + '\'' +
                ", email='" + email + '\'' +
                ", telefon=" + telefon +
                '}';
    }
}
