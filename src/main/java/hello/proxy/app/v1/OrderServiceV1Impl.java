package hello.proxy.app.v1;

public class OrderServiceV1Impl implements OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;

    public OrderServiceV1Impl(OrderRepositoryV2 orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
