import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Базовий клас Media
abstract class Media {
    private String title;
    private String genre;

    public Media(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Назва: " + title + ", Жанр: " + genre;
    }
}

// Клас Book
class Book extends Media {
    private String author;

    public Book(String title, String genre, String author) {
        super(title, genre);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Книга — " + super.toString() + ", Автор: " + author;
    }
}

// Клас Magazine
class Magazine extends Media {
    private int issueNumber;

    public Magazine(String title, String genre, int issueNumber) {
        super(title, genre);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String toString() {
        return "Журнал — " + super.toString() + ", Номер випуску: " + issueNumber;
    }
}

// Клас DVD
class DVD extends Media {
    private int duration; // тривалість у хвилинах

    public DVD(String title, String genre, int duration) {
        super(title, genre);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "DVD — " + super.toString() + ", Тривалість: " + duration + " хв";
    }
}

// Клас Library
class Library<T extends Media> {
    private List<T> items;

    public Library() {
        this.items = new ArrayList<>();
    }

    // Метод додавання
    public void addItem(T item) {
        items.add(item);
    }

    // Метод видалення
    public boolean removeItem(String title) {
        return items.removeIf(item -> item.getTitle().equalsIgnoreCase(title));
    }

    // Метод пошуку
    public List<T> search(String keyword) {
        return items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        item.getGenre().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Метод для відображення всіх елементів
    public void displayAll() {
        if (items.isEmpty()) {
            System.out.println("Бібліотека порожня.");
        } else {
            items.forEach(System.out::println);
        }
    }
}

// Головний клас
public class LibraryApp {
    public static void main(String[] args) {
        Library<Media> library = new Library<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати книгу");
            System.out.println("2. Додати журнал");
            System.out.println("3. Додати DVD");
            System.out.println("4. Видалити медіа");
            System.out.println("5. Шукати медіа");
            System.out.println("6. Відобразити всі медіа");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очищення буфера

            switch (choice) {
                case 1 -> {
                    System.out.print("Введіть назву книги: ");
                    String title = scanner.nextLine();
                    System.out.print("Введіть жанр книги: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введіть автора книги: ");
                    String author = scanner.nextLine();
                    library.addItem(new Book(title, genre, author));
                }
                case 2 -> {
                    System.out.print("Введіть назву журналу: ");
                    String title = scanner.nextLine();
                    System.out.print("Введіть жанр журналу: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введіть номер випуску журналу: ");
                    int issueNumber = scanner.nextInt();
                    library.addItem(new Magazine(title, genre, issueNumber));
                }
                case 3 -> {
                    System.out.print("Введіть назву DVD: ");
                    String title = scanner.nextLine();
                    System.out.print("Введіть жанр DVD: ");
                    String genre = scanner.nextLine();
                    System.out.print("Введіть тривалість (у хвилинах): ");
                    int duration = scanner.nextInt();
                    library.addItem(new DVD(title, genre, duration));
                }
                case 4 -> {
                    System.out.print("Введіть назву медіа для видалення: ");
                    String title = scanner.nextLine();
                    if (library.removeItem(title)) {
                        System.out.println("Медіа видалено.");
                    } else {
                        System.out.println("Медіа з такою назвою не знайдено.");
                    }
                }
                case 5 -> {
                    System.out.print("Введіть ключове слово для пошуку: ");
                    String keyword = scanner.nextLine();
                    List<Media> results = library.search(keyword);
                    if (results.isEmpty()) {
                        System.out.println("Нічого не знайдено.");
                    } else {
                        System.out.println("Знайдено:");
                        results.forEach(System.out::println);
                    }
                }
                case 6 -> library.displayAll();
                case 0 -> {
                    System.out.println("До побачення!");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
