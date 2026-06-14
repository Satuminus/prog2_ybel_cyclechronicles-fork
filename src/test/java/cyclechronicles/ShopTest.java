package cyclechronicles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class ShopTest {

    private Order order(Type type, String customer) {
        Order order = mock(Order.class);
        when(order.getBicycleType()).thenReturn(type);
        when(order.getCustomer()).thenReturn(customer);
        return order;
    }

    @Test
    void acceptsValidRaceBikeOrder() {
        Shop shop = new Shop();

        Order order = order(Type.RACE, "Felix");

        assertTrue(shop.accept(order));
    }

    @Test
    void rejectsGravelBikeOrder() {
        Shop shop = new Shop();

        Order order = order(Type.GRAVEL, "Felix");

        assertFalse(shop.accept(order));
    }

    @Test
    void rejectsEbikeOrder() {
        Shop shop = new Shop();

        Order order = order(Type.EBIKE, "Felix");

        assertFalse(shop.accept(order));
    }

    @Test
    void rejectsSecondPendingOrderForSameCustomer() {
        Shop shop = new Shop();

        Order firstOrder = order(Type.RACE, "Felix");
        Order secondOrder = order(Type.RACE, "Felix");

        assertTrue(shop.accept(firstOrder));
        assertFalse(shop.accept(secondOrder));
    }

    @Test
    void acceptsOrderWhenFourOrdersArePending() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer1")));
        assertTrue(shop.accept(order(Type.RACE, "Customer2")));
        assertTrue(shop.accept(order(Type.RACE, "Customer3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer4")));

        Order fifthOrder = order(Type.RACE, "Customer5");

        assertTrue(shop.accept(fifthOrder));
    }

    @Test
    void rejectsOrderWhenFiveOrdersArePending() {
        Shop shop = new Shop();

        assertTrue(shop.accept(order(Type.RACE, "Customer1")));
        assertTrue(shop.accept(order(Type.RACE, "Customer2")));
        assertTrue(shop.accept(order(Type.RACE, "Customer3")));
        assertTrue(shop.accept(order(Type.RACE, "Customer4")));
        assertTrue(shop.accept(order(Type.RACE, "Customer5")));

        Order sixthOrder = order(Type.RACE, "Customer6");

        assertFalse(shop.accept(sixthOrder));
    }
}
