import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class University {

    private String name;
    private List<Department> departments;

    public University(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    // Внутрішній клас Department
    public class Department {
        private String name;
        private List<String> professors;

        public Department(String name) {
            this.name = name;
            this.professors = new ArrayList<>();
        }

        public void addProfessor(String professor) {
            professors.add(professor);
        }

        public String getName() {
            return name;
        }

        public List<String> getProfessors() {
            return professors;
        }

        @Override
        public String toString() {
            return "Кафедра: " + name + ", Викладачі: " + professors;
        }
    }

    // Статичний вкладений клас Helper
    public static class Helper {
        public static double calculateAverageGrade(List<Integer> grades) {
            if (grades.isEmpty()) return 0.0;
            int sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return (double) sum / grades.size();
        }
    }

    // Метод для управління подіями з використанням анонімного класу
    public void manageEvents() {
        EventManager eventManager = new EventManager() {
            @Override
            public void organizeEvent(String eventName) {
                System.out.println("Подія \"" + eventName + "\" успішно організована університетом " + name);
            }
        };

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть назву події для організації: ");
        String eventName = scanner.nextLine();
        eventManager.organizeEvent(eventName);
    }

    // Інтерфейс для анонімного класу
    interface EventManager {
        void organizeEvent(String eventName);
    }

    // Метод для додавання кафедри
    public void addDepartment(String departmentName) {
        departments.add(new Department(departmentName));
    }

    // Метод для перегляду кафедр
    public void displayDepartments() {
        if (departments.isEmpty()) {
            System.out.println("Кафедр немає.");
        } else {
            for (Department department : departments) {
                System.out.println(department);
            }
        }
    }

    // Взаємодія з користувачем
    public static void main(String[] args) {
        University university = new University("Університет Прикладних Наук");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати кафедру");
            System.out.println("2. Додати викладача до кафедри");
            System.out.println("3. Переглянути кафедри");
            System.out.println("4. Організувати подію");
            System.out.println("5. Розрахувати середній бал");
            System.out.println("0. Вийти");

            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очищення буфера

            switch (choice) {
                case 1 -> {
                    System.out.print("Введіть назву кафедри: ");
                    String departmentName = scanner.nextLine();
                    university.addDepartment(departmentName);
                }
                case 2 -> {
                    System.out.print("Введіть назву кафедри: ");
                    String departmentName = scanner.nextLine();
                    University.Department department = university.findDepartment(departmentName);
                    if (department != null) {
                        System.out.print("Введіть ім'я викладача: ");
                        String professorName = scanner.nextLine();
                        department.addProfessor(professorName);
                    } else {
                        System.out.println("Кафедру не знайдено.");
                    }
                }
                case 3 -> university.displayDepartments();
                case 4 -> university.manageEvents();
                case 5 -> {
                    System.out.print("Введіть кількість оцінок: ");
                    int count = scanner.nextInt();
                    List<Integer> grades = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        System.out.print("Введіть оцінку " + (i + 1) + ": ");
                        grades.add(scanner.nextInt());
                    }
                    double average = Helper.calculateAverageGrade(grades);
                    System.out.println("Середній бал: " + average);
                }
                case 0 -> {
                    System.out.println("До побачення!");
                    return;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    // Метод для пошуку кафедри за назвою
    private Department findDepartment(String name) {
        for (Department department : departments) {
            if (department.getName().equalsIgnoreCase(name)) {
                return department;
            }
        }
        return null;
    }
}
