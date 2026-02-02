package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardParcelBox =
            new ParcelBox<>("Стандартные посылки", 50.0);
    private static ParcelBox<FragileParcel> fragileParcelBox =
            new ParcelBox<>("Хрупкие посылки", 20.0);
    private static ParcelBox<PerishableParcel> perishableParcelBox =
            new ParcelBox<>("Скоропортящиеся посылки", 30.0);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addParcel();
                case 2 -> sendParcels();
                case 3 -> calculateCosts();
                case 4 -> reportStatus();
                case 5 -> showContentsBox();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отправить статус отслеживания");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите из предложенных какой у вас тип посылки?");
        System.out.println("Стандартная посылка - 1");
        System.out.println("Хрупкая посылка - 2");
        System.out.println("Скоропортящаяся посылка - 3");
        int typeParcel = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите описание посылки: ");
        String parcelDescription = scanner.nextLine();

        System.out.print("Введите вес посылки: ");
        int parcelWeight = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес доставки: ");
        String parcelDeliveryAddress = scanner.nextLine();

        System.out.print("Введите день отправки: ");
        int parcelSendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel;

        switch (typeParcel) {
            case 1:
                parcel = new StandardParcel(parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay);
                standardParcelBox.addParcel((StandardParcel) parcel);
                break;
            case 2:
                FragileParcel fragileParcel = new FragileParcel(parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay);
                parcel = fragileParcel;
                trackableParcels.add(fragileParcel);
                fragileParcelBox.addParcel(fragileParcel);
                break;
            case 3:
                System.out.print("Введите срок хранения посылки: ");
                int parcelTimeToLive = Integer.parseInt(scanner.nextLine());
                parcel = new PerishableParcel(parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay, parcelTimeToLive);
                perishableParcelBox.addParcel((PerishableParcel) parcel);
                break;
            default:
                System.out.println("Неверный тип посылки. Создана стандартная посылка.");
                parcel = new StandardParcel(parcelDescription, parcelWeight,
                        parcelDeliveryAddress, parcelSendDay);
        }

        allParcels.add(parcel);

        System.out.println("Посылка добавлена успешно!\n");
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.\n");
            return;
        }

        System.out.print("Введите текущий день месяца: ");
        int currentDay = Integer.parseInt(scanner.nextLine());

        int i = 1;
        for (Parcel parcel : allParcels) {
            System.out.println("Посылка -" + i++ + "- :");

            if (parcel instanceof PerishableParcel perishableParcel) {
                if (perishableParcel.isExpired(currentDay)) {
                    System.out.println("ВНИМАНИЕ! Посылка <<" + parcel.getDescription() +
                            ">> испортилась и не может быть отправлена!");
                    continue;
                }
            }
            parcel.packageItem();
            parcel.deliver();
            System.out.println();
        }
        System.out.println("Все посылки отправлены!\n");
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчета стоимости.\n");
            return;
        }

        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            int cost = parcel.calculateDeliveryCost();
            totalCost += cost;
        }
        System.out.println("Общая стоимость доставки: " + totalCost + " руб.\n");
    }

    private static void reportStatus() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отслеживания.\n");
            return;
        }

        System.out.println("Введите новое состояние посылки.");
        String newLocation =  scanner.nextLine();

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
        System.out.println();
    }

    private static void showContentsBox() {
        System.out.println("Выберите коробку для просмотра содержимого:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");
        System.out.println("0 — Вернуться в меню");

        boolean run = true;
        while (run) {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Содержимое коробки: " + standardParcelBox.getBoxName() + " ===");
                    System.out.println("Максимальный вес: " + standardParcelBox.getMaxWeight() + " кг");
                    System.out.println("Текущий вес: " + standardParcelBox.getCurrentWeight() + " кг");
                    System.out.println("Количество посылок: " + standardParcelBox.getParcelCount());
                    standardParcelBox.getAllParcels();
                }
                case 2 -> {
                    System.out.println("Содержимое коробки: " + fragileParcelBox.getBoxName() + " ===");
                    System.out.println("Максимальный вес: " + fragileParcelBox.getMaxWeight() + " кг");
                    System.out.println("Текущий вес: " + fragileParcelBox.getCurrentWeight() + " кг");
                    System.out.println("Количество посылок: " + fragileParcelBox.getParcelCount());
                    fragileParcelBox.getAllParcels();
                }
                case 3 -> {
                    System.out.println("Содержимое коробки: " + perishableParcelBox.getBoxName() + " ===");
                    System.out.println("Максимальный вес: " + perishableParcelBox.getMaxWeight() + " кг");
                    System.out.println("Текущий вес: " + perishableParcelBox.getCurrentWeight() + " кг");
                    System.out.println("Количество посылок: " + perishableParcelBox.getParcelCount());
                    perishableParcelBox.getAllParcels();
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }    System.out.println();
    }
}