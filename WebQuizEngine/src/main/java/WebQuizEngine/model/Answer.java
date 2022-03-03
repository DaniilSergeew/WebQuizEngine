package WebQuizEngine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.TreeSet;

@NoArgsConstructor
@Setter
@Getter
public class Answer {
    private TreeSet<Integer> answer;

    public TreeSet<Integer> getAnswer() {
        if (Objects.equals(null, answer)) {
            return new TreeSet<>();
        }
        return this.answer;
    }

}
