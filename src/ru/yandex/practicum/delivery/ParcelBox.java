package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels  = new ArrayList<>();
    private final String boxName;
    private final double maxWeight;
    private double currentWeight = 0;

    public ParcelBox(String boxName, double maxWeight) {
        this.boxName = boxName;
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() <= maxWeight) {
            parcels.add(parcel);
            currentWeight += parcel.getWeight();
            System.out.println("Посылка добавлена в коробку '" + boxName + "'");
            System.out.println("Текущий вес коробки: " + currentWeight + "/" + maxWeight + " кг");
            return true;
        } else {
            System.out.println("Ошибка! Превышен максимальный вес коробки '" + boxName + "'");
            System.out.println("Доступно: " + (maxWeight - currentWeight) + " кг");
            return false;
        }
    }

    public void getAllParcels() {
        if (parcels.isEmpty()) {
            System.out.println("Коробка '" + boxName + "' пуста");
            return;
        }

        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = parcels.get(i);
            System.out.println((i + 1) + ". " + parcel.getDescription() +
                    " - " + parcel.getWeight() + " кг");
        }
    }

    public String getBoxName() {
        return boxName;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public int getParcelCount() {
        return parcels.size();
    }
}
