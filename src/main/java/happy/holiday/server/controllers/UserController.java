package happy.holiday.server.controllers;

import happy.holiday.server.dto.UserDto;
import happy.holiday.server.entity.SellerEntity;
import happy.holiday.server.entity.UserEntity;
import happy.holiday.server.factory.UserDtoFactory;
import happy.holiday.server.repository.SellerRepository;
import happy.holiday.server.repository.UserRepository;
import happy.holiday.server.exception.NotFoundException;
import happy.holiday.server.exception.IllegalArgumentException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequiredArgsConstructor
class UserController {

	private final UserRepository userRepository;
	private final UserDtoFactory userDtoFactory;

	private final SellerRepository sellerRepository;

	public static final String AUTHORIZE    = "api/users/authorize";
	public static final String REGISTRATION = "api/users/registration";
	public static final String SEARCH = "api/users/search";

	@Transactional
	@GetMapping(AUTHORIZE)
	public ResponseEntity<UserDto> login(@RequestParam String phoneNumber,
								   	     @RequestParam String password) throws NotFoundException {
			
		var userEntity = userRepository
			.findTopByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new NotFoundException("User was not found"));  
		
		if (!userEntity.getPassword().equals(password)) {
			throw new IllegalArgumentException("Password is not correct");
		}

		return ResponseEntity.ok(userDtoFactory.createUserDto(userEntity));
	}


	/*
	 * FIX:
	 *  -> Возвращает 200-код при успешной регистрации, нужно поправить на 201-ый
	 *  -> также почему-то при уже записанном номере не выкидывает ошибку 
	 * Аналогичные ошибки также есть в SellerController
	 */

	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(REGISTRATION)
	public ResponseEntity<String> registration(@RequestParam String phoneNumber,
								   	     	   @RequestParam String password,
											   @RequestParam String fio,
											   @RequestParam Integer age) throws NotFoundException {
			
		userRepository
			.findTopByPhoneNumber(phoneNumber)
			.ifPresentOrElse(
				(res) -> new IllegalArgumentException(
					String.format("User with %s number is exists", phoneNumber)),
				() -> userRepository.saveAndFlush(
					new UserEntity().builder()
									.phoneNumber(phoneNumber)
									.password(password)
									.fio(fio)
									.age(age)
									.build()
				));
			
			
		return ResponseEntity.ok("OK");
	}

	/*
	TODO реализовать функцию(метод) в контроллере
	который получает запрос(любой)
	а возвращает первые 5 строчек с таблицы селлерс(название услуги и ее цена)
	отправляет их в формате json - строчки на GUI


	 */

	/*

	@Transactional
	@GetMapping(SEARCH)
	public ResponseEntity<String> GetFiveTop()  throws NotFoundException {

		var sellerEntities = sellerRepository
				.findAll()
				.subList(0, 5);

		return ResponseEntity.ok(sellerEntities.toString());
	}
	//*/
	//второй вариант, через try
	// TODO проверить функцию вывода списка продавцов

	//*
	@Transactional
	public ResponseEntity<String> GetFiveTop() throws NotFoundException{

		try {
			var sellerEntities = sellerRepository.findAll().subList(0, 5);

			return ResponseEntity.ok(sellerEntities.toString());
		} catch (Exception e) {
			throw new NotFoundException("No sellers");
		}
	}
	//*/
}

