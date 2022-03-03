package WebQuizEngine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.TreeSet;

@Table(name = "Quizzes")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quiz {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @NotBlank(message = "Field title should not be empty ")
    private String title;

    @Column
    @NotBlank(message = "Field text Question should not be empty!")
    private String text;

    @Column
    @NotNull(message = "Field options Question should not be empty!")
    @Size(min = 2, message = "Options must be greater than or equal to 2!")
    private String[] options;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TreeSet<Integer> answer;

    @Column
    @JsonIgnore
    private String author;

    public Quiz(String title,
                String text,
                String[] options,
                TreeSet<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public TreeSet<Integer> getAnswer() {
        if (Objects.equals(null, answer)) {
            return new TreeSet<>();
        }
        return this.answer;
    }
}
