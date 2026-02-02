package ru.yandex.practicum;
import ru.yandex.practicum.delivery.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpoiledParcelTest {

    @Test
    public void testIsExpired_NotExpired() {
        // Стандартный сценарий - посылка не испортилась
        PerishableParcel parcel = new PerishableParcel(
                "Молоко", 1, "Москва", 1, 3);
        // 1 + 3 >= 3 -> false (не испортилась)
        assertFalse(parcel.isExpired(3));

        // Точная граница - в последний день срока еще не испортилась
        // 5 + 2 >= 7 -> false (не испортилась)
        PerishableParcel parcel2 = new PerishableParcel(
                "Йогурт", 1, "Москва", 5, 2);
        assertFalse(parcel2.isExpired(7));
    }

    @Test
    public void testIsExpired_Expired() {
        // Стандартный сценарий - посылка испортилась
        PerishableParcel parcel = new PerishableParcel(
                "Рыба", 1, "Москва", 1, 2);
        // 1 + 2 < 4 -> true (испортилась)
        assertTrue(parcel.isExpired(4));

        // Граничный случай - срок хранения 0 дней
        PerishableParcel zeroShelfLife = new PerishableParcel(
                "Суши", 1, "Москва", 1, 0);
        // 1 + 0 < 2 -> true (испортилась)
        assertTrue(zeroShelfLife.isExpired(2));
    }

    @Test
    public void testIsExpired_BoundaryCases() {
        // Граничный случай - посылка отправлена сегодня
        PerishableParcel parcel = new PerishableParcel(
                "Фрукты", 1, "Москва", 10, 5);
        // 10 + 5 >= 10 -> false (не испортилась)
        assertFalse(parcel.isExpired(10));

        // Граничный случай - посылка испортится завтра
        // 10 + 5 < 15 -> true (испортилась)
        assertTrue(parcel.isExpired(15));
    }
}