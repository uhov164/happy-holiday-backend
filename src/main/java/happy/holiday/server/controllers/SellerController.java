package happy.holiday.server.controllers;


import happy.holiday.server.dto.SellerDto;
import happy.holiday.server.entity.SellerEntity;
import happy.holiday.server.exception.NotFoundException;
import happy.holiday.server.factory.SellerDtoFactory;
import happy.holiday.server.repository.SellerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
class SellerController { 

    private final SellerRepository sellerRepository;
    private final SellerDtoFactory sellerDtoFactory;

    public static final String AUTHORIZE    = "api/sellers/authorize";
    public static final String REGISTRATION = "api/sellers/registration";

	@Transactional
	@GetMapping(AUTHORIZE)
	public ResponseEntity<SellerDto> login(@RequestParam String phoneNumber,
                                           @RequestParam String password) throws NotFoundException {
			
		var sellerEntity = sellerRepository
			.findTopByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new NotFoundException("Seller was not found"));  
		
		if (!sellerEntity.getPassword().equals(password)) {
			throw new IllegalArgumentException("Password is not correct");
		}

		return ResponseEntity.ok(sellerDtoFactory.createSellerDto(sellerEntity));
	}

    /*
     * смотреть в UserController(FIX:registration)
     */

	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(REGISTRATION)
	public ResponseEntity<String> registration(@RequestParam String phoneNumber,
								   	     	   @RequestParam String password,
											   @RequestParam String fio,
											   @RequestParam Integer age) throws NotFoundException {
			
		sellerRepository
			.findTopByPhoneNumberAndPassword(phoneNumber, password)
			.ifPresentOrElse(
				(res) -> new IllegalArgumentException(
					String.format("User with %s number is exists", phoneNumber)),
				() -> sellerRepository.saveAndFlush(
					new SellerEntity().builder()
									  .phoneNumber(phoneNumber)
									  .password(password)
									  .fio(fio)
									  .age(age)
									  .build()
				));
			
			
		return ResponseEntity.ok("OK");
	}
}
