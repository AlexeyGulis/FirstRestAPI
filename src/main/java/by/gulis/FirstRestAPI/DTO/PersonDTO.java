package by.gulis.FirstRestAPI.DTO;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 200, message = "Имя должно быть длинной от 2 до 200 символов")
    private String fullName;

    @Min(value = 1900, message = "Год должен быть больше чем 1900")
    private int dateOfBirth;

    public PersonDTO() {
    }

    public PersonDTO(String fullName, int dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
