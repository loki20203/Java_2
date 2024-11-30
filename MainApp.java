import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Logger — Singleton
class Logger {
    private static Logger instance;
    private boolean logToFile;
    private FileWriter fileWriter;

    private Logger() {
        this.logToFile = false;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void enableFileLogging(String filePath) {
        try {
            fileWriter = new FileWriter(filePath, true);
            logToFile = true;
        } catch (IOException e) {
            logToFile = false;
            log("Не вдалося увімкнути логування у файл: " + e.getMessage());
        }
    }

    public void log(String message) {
        if (logToFile && fileWriter != null) {
            try {
                fileWriter.write(message + "\n");
                fileWriter.flush();
            } catch (IOException e) {
                System.out.println("Помилка запису у файл: " + e.getMessage());
            }
        } else {
            System.out.println(message);
        }
    }
}

// Інтерфейс Notification
interface Notification {
    void send(String message);
}

// EmailNotification
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        Logger.getInstance().log("Email сповіщення: " + message);
    }
}

// SMSNotification
class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        Logger.getInstance().log("SMS сповіщення: " + message);
    }
}

// PushNotification
class PushNotification implements Notification {
    @Override
    public void send(String message) {
        Logger.getInstance().log("Push сповіщення: " + message);
    }
}

// Factory для створення Notification
class NotificationFactory {
    public static Notification createNotification(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms" -> new SMSNotification();
            case "push" -> new PushNotification();
            default -> throw new IllegalArgumentException("Невідомий тип сповіщення: " + type);
        };
    }
}

// Інтерфейс Subscriber
interface Subscriber {
    void update(String news);
}

// Клас NewsAgency
class NewsAgency {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void generateNews(String news) {
        Logger.getInstance().log("Генерується новина: " + news);
        notifySubscribers(news);
    }

    private void notifySubscribers(String news) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(news);
        }
    }
}

// Реалізація Subscriber
class UserSubscriber implements Subscriber {
    private String name;

    public UserSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        Logger.getInstance().log(name + " отримав новину: " + news);
    }
}

// Головний клас
public class MainApp {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть шлях до файлу логів (або залиште порожнім для логів у консоль):");
        String filePath = scanner.nextLine();
        if (!filePath.isEmpty()) {
            logger.enableFileLogging(filePath);
        }

        NotificationFactory notificationFactory = new NotificationFactory();
        NewsAgency newsAgency = new NewsAgency();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Створити сповіщення");
            System.out.println("2. Додати підписника");
            System.out.println("3. Видалити підписника");
            System.out.println("4. Згенерувати новину");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера

            switch (choice) {
                case 1 -> {
                    System.out.println("Виберіть тип сповіщення (email, sms, push):");
                    String type = scanner.nextLine();
                    try {
                        Notification notification = NotificationFactory.createNotification(type);
                        System.out.println("Введіть текст сповіщення:");
                        String message = scanner.nextLine();
                        notification.send(message);
                    } catch (IllegalArgumentException e) {
                        logger.log(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Введіть ім'я підписника:");
                    String name = scanner.nextLine();
                    UserSubscriber subscriber = new UserSubscriber(name);
                    newsAgency.addSubscriber(subscriber);
                    logger.log("Додано підписника: " + name);
                }
                case 3 -> {
                    System.out.println("Введіть ім'я підписника для видалення:");
                    String name = scanner.nextLine();
                    UserSubscriber toRemove = new UserSubscriber(name);
                    newsAgency.removeSubscriber(toRemove);
                    logger.log("Видалено підписника: " + name);
                }
                case 4 -> {
                    System.out.println("Введіть текст новини:");
                    String news = scanner.nextLine();
                    newsAgency.generateNews(news);
                }
                case 0 -> {
                    logger.log("Програма завершена.");
                    return;
                }
                default -> logger.log("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
