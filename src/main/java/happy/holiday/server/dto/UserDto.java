package happy.holiday.server.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
	private String phone_number;
	private String fio;
	private int age;
}

