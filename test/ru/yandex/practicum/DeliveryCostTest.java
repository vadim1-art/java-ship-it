package ru.yandex.practicum;
import ru.yandex.practicum.delivery.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {

    // StandardParcel

    @Test
    public void testStandardParcelCost_NormalWeight() {
        StandardParcel parcel = new StandardParcel(
                "Книги", 5, "Москва", 1);
        assertEquals(10, parcel.calculateDeliveryCost()); // 5 * 2 = 10
    }

    @Test
    public void testStandardParcelCost_MinimumWeight() {
        StandardParcel parcel = new StandardParcel(
                "Письмо", 1, "Москва", 1);
        assertEquals(2, parcel.calculateDeliveryCost()); // 1 * 2 = 2
    }

    @Test
    public void testStandardParcelCost_ZeroWeight() {
        StandardParcel parcel = new StandardParcel(
                "Электронное письмо", 0, "Москва", 1);
        assertEquals(0, parcel.calculateDeliveryCost()); // 0 * 2 = 0
    }

    // FragileParcel

    @Test
    public void testFragileParcelCost_NormalWeight() {
        FragileParcel parcel = new FragileParcel(
                "Ваза", 3, "Санкт-Петербург", 1);
        assertEquals(12, parcel.calculateDeliveryCost()); // 3 * 4 = 12
    }

    @Test
    public void testFragileParcelCost_LargeWeight() {
        FragileParcel parcel = new FragileParcel(
                "Хрустальная люстра", 50, "Москва", 1);
        assertEquals(200, parcel.calculateDeliveryCost()); // 50 * 4 = 200
    }

    @Test
    public void testFragileParcelCost_ZeroWeight() {
        FragileParcel parcel = new FragileParcel(
                "Пустая упаковка", 0, "Москва", 1);
        assertEquals(0, parcel.calculateDeliveryCost()); // 0 * 4 = 0
    }

    // PerishableParcel

    @Test
    public void testPerishableParcelCost_NormalWeight() {
        PerishableParcel parcel = new PerishableParcel(
                "Рыба", 2, "Москва", 1, 3);
        assertEquals(6, parcel.calculateDeliveryCost()); // 2 * 3 = 6
    }

    @Test
    public void testPerishableParcelCost_ZeroWeight() {
        PerishableParcel parcel = new PerishableParcel(
                "Письмо", 0, "Москва", 1, 1);
        assertEquals(0, parcel.calculateDeliveryCost()); // 0 * 3 = 0
    }

    @Test
    public void testPerishableParcelCost_MinimumWeight() {
        PerishableParcel parcel = new PerishableParcel(
                "Конфета", 1, "Москва", 1, 7);
        assertEquals(3, parcel.calculateDeliveryCost()); // 1 * 3 = 3
    }
}