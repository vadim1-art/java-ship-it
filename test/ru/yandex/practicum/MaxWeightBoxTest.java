package ru.yandex.practicum;
import ru.yandex.practicum.delivery.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MaxWeightBoxTest {

    @Test
    public void testAddParcel_Success() {
        // Стандартный сценарий - добавление посылки
        ParcelBox<StandardParcel> box = new ParcelBox<>(
                "Тестовая коробка", 10.0);
        StandardParcel parcel = new StandardParcel(
                "Книга", 3, "Москва", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(3.0, box.getCurrentWeight(), 0.01);
        assertEquals(1, box.getParcelCount());
    }

    @Test
    public void testAddParcel_MultipleParcels() {
        // Добавление нескольких посылок
        ParcelBox<FragileParcel> box = new ParcelBox<>("Хрупкие", 15.0);
        FragileParcel parcel1 = new FragileParcel(
                "Ваза", 5, "Москва", 1);
        FragileParcel parcel2 = new FragileParcel(
                "Статуэтка", 4, "Москва", 1);

        assertTrue(box.addParcel(parcel1));
        assertTrue(box.addParcel(parcel2));
        assertEquals(9.0, box.getCurrentWeight(), 0.01);
        assertEquals(2, box.getParcelCount());
    }

    @Test
    public void testAddParcel_ExceedMaxWeight() {
        // Граничный случай - превышение максимального веса
        ParcelBox<PerishableParcel> box = new ParcelBox<>("Скоропортящиеся", 5.0);
        PerishableParcel parcel1 = new PerishableParcel(
                "Молоко", 3, "Москва", 1, 2);
        PerishableParcel parcel2 = new PerishableParcel(
                "Рыба", 3, "Москва", 1, 1);

        // Первая посылка добавляется успешно
        assertTrue(box.addParcel(parcel1));
        assertEquals(3.0, box.getCurrentWeight(), 0.01);
        assertEquals(1, box.getParcelCount());

        // Вторая посылка не должна добавиться
        assertFalse(box.addParcel(parcel2));
        assertEquals(3.0, box.getCurrentWeight(), 0.01); // Вес не изменился
        assertEquals(1, box.getParcelCount()); // Количество не изменилось
    }

    @Test
    public void testAddParcel_BoundaryWeight() {
        // Граничный случай - точное соответствие максимальному весу
        ParcelBox<StandardParcel> box = new ParcelBox<>("Граничная", 10.0);
        StandardParcel parcel = new StandardParcel(
                "Тяжелая книга", 10, "Москва", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(10.0, box.getCurrentWeight(), 0.01);
        assertEquals(1, box.getParcelCount());
    }

    @Test
    public void testAddParcel_ZeroWeight() {
        // Граничный случай - посылка с нулевым весом
        ParcelBox<StandardParcel> box = new ParcelBox<>("Пустая", 5.0);
        StandardParcel parcel = new StandardParcel(
                "Письмо", 0, "Москва", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(0.0, box.getCurrentWeight(), 0.01);
        assertEquals(1, box.getParcelCount());
    }
}