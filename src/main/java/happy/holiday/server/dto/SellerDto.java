package happy.holiday.server.dto;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;	// чета мне подскзывает что это тут тоже надо,
									// хотя у меня вопрос - что оно делает (конкретно)

@Data
@AllArgsConstructor					//если оно не надо - то уберу
public class SellerDto {
	private String phone_number;
	private String fio;
	private int age;

	private List<ItemDto> items;
	private List<OrderDto> orderHistory;
}
