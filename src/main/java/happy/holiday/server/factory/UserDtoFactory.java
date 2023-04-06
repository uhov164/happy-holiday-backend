package happy.holiday.server.factory;

import org.springframework.stereotype.Component;

import happy.holiday.server.dto.UserDto;
import happy.holiday.server.entity.UserEntity;
import jakarta.transaction.Transactional;

//Зачем тут компонент?
@Component
public class UserDtoFactory {

	@Transactional
	public UserDto createUserDto(UserEntity userEntity) {

		var phoneNumber = userEntity.getPhoneNumber();
		var fio = userEntity.getFio();
		var age = userEntity.getAge();

		return new UserDto(phoneNumber, fio, age);
	}
}
