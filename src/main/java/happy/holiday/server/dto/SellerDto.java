package happy.holiday.server.dto;

import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;	
									

@Data
@AllArgsConstructor					
public class SellerDto {
	private String phoneNumber;
	private String fio;
	private int age;
}
