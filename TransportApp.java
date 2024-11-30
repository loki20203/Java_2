import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Абстрактний клас Vehicle
abstract class Vehicle {
    protected String make;
    protected String model;
    protected int year;

    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public abstract void start();

    public abstract void stop();

    @Override
    public String toString() {
        return year + " " + make + " " + model;
    }
}

// Інтерфейс Refuelable
interface Refuelable {
    void refuel();
}

// Клас Car
class Car extends Vehicle implements Refuelable {
    public Car(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public void start() {
        System.out.println("Car " + this + " is starting.");
    }

    @Override
    public void stop() {
        System.out.println("Car " + this + " is stopping.");
    }

    @Override
    public void refuel() {
        System.out.println("Car " + this + " is refueling.");
    }
}

// Клас Bike
class Bike extends Vehicle {
    public Bike(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public void start() {
        System.out.println("Bike " + this + " is starting.");
    }

    @Override
    public void stop() {
        System.out.println("Bike " + this + " is stopping.");
    }
}

// Клас Truck
class Truck extends Vehicle implements Refuelable {
    public Truck(String make, String model, int year) {
        super(make, model, year);
    }

    @Override
    public void start() {
        System.out.println("Truck " + this + " is starting.");
    }

    @Override
    public void stop() {
        System.out.println("Truck " + this + " is stopping.");
    }

    @Override
    public void refuel() {
        System.out.println("Truck " + this + " is refueling.");
    }
}

// Основний клас
public class TransportApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Vehicle> vehicles = new ArrayList<>();

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати транспортний засіб");
            System.out.println("2. Показати всі транспортні засоби");
            System.out.println("3. Виконати дію (запуск, зупинка, заправка)");
            System.out.println("4. Вийти");
            System.out.print("Ваш вибір: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("\nОберіть тип транспортного засобу:");
                    System.out.println("1. Автомобіль");
                    System.out.println("2. Мотоцикл");
                    System.out.println("3. Вантажівка");
                    int type = Integer.parseInt(scanner.nextLine());

                    System.out.print("Введіть марку: ");
                    String make = scanner.nextLine();
                    System.out.print("Введіть модель: ");
                    String model = scanner.nextLine();
                    System.out.print("Введіть рік випуску: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    switch (type) {
                        case 1 -> vehicles.add(new Car(make, model, year));
                        case 2 -> vehicles.add(new Bike(make, model, year));
                        case 3 -> vehicles.add(new Truck(make, model, year));
                        default -> System.out.println("Невірний вибір типу.");
                    }
                    System.out.println("Транспортний засіб додано.");
                }
                case 2 -> {
                    System.out.println("\nСписок транспортних засобів:");
                    if (vehicles.isEmpty()) {
                        System.out.println("Список порожній.");
                    } else {
                        vehicles.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    if (vehicles.isEmpty()) {
                        System.out.println("Список транспортних засобів порожній.");
                        break;
                    }

                    System.out.println("\nОберіть транспортний засіб:");
                    for (int i = 0; i < vehicles.size(); i++) {
                        System.out.println((i + 1) + ". " + vehicles.get(i));
                    }
                    int vehicleIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                        System.out.println("Невірний вибір.");
                        break;
                    }

                    Vehicle vehicle = vehicles.get(vehicleIndex);

                    System.out.println("\nОберіть дію:");
                    System.out.println("1. Запуск");
                    System.out.println("2. Зупинка");
                    System.out.println("3. Заправка (якщо доступно)");
                    int action = Integer.parseInt(scanner.nextLine());

                    switch (action) {
                        case 1 -> vehicle.start();
                        case 2 -> vehicle.stop();
                        case 3 -> {
                            if (vehicle instanceof Refuelable) {
                                ((Refuelable) vehicle).refuel();
                            } else {
                                System.out.println("Цей транспортний засіб не можна заправляти.");
                            }
                        }
                        default -> System.out.println("Невірна дія.");
                    }
                }
                case 4 -> {
                    System.out.println("Програма завершена.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }
}
