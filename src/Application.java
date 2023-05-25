import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    private final List<String> names;
    private final List<String> surnames;
    private final Collection<Person> persons;

    public Application() {
        names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        surnames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        persons = new ArrayList<>();
        generate();
    }

    public void generate() {
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                            names.get(new Random().nextInt(names.size())),
                            surnames.get(new Random().nextInt(surnames.size())),
                            new Random().nextInt(100),
                            Sex.values()[new Random().nextInt(Sex.values().length)],
                            Education.values()[new Random().nextInt(Education.values().length)]
                    )
            );
        }
    }

    public void run() {
//1 Для поиска несовершеннолетних используйте промежуточный метод filter() и терминальный метод count()
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
//2
/*
Для получения списка призывников потребуется применить несколько промежуточных методов filter(),
а также для преобразования данных из Person в String (так как нужны только фамилии) используйте метод map().
Так как требуется получить список List<String> терминальным методом будет collect(Collectors.toList())
 */
        List<String> conscript = persons.stream()
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex().equals(Sex.MAN))
                .map(person -> person.getName())
                .collect(Collectors.toList());
//3
/*
Для получения отсортированного по фамилии списка потенциально работоспособных людей с высшим образованием
необходимо применить ряд промежуточных методов filter(), метод sorted() в который нужно будет положить компаратор
по фамилиям Comparator.comparing(). Завершить стрим необходимо методом collect()
 */
        List<Person> personList = persons.stream()
                .filter((person) -> (person.getAge() >= 18 && person.getAge() <= 60 && person.getSex().equals(Sex.WOMAN))
                                 || (person.getAge() >= 18 && person.getAge() <= 65 && person.getSex().equals(Sex.MAN)))
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .sorted(Comparator.comparing(Person::getSurname))
                .collect(Collectors.toList());
    }
}
