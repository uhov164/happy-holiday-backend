package happy.holiday.server.controllers;


import happy.holiday.server.dto.SellerDto;
import happy.holiday.server.factory.SellerDtoFactory;
import happy.holiday.server.repository.SellerRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // почему не @Controller?
class SellerController { // почему не пишем public?

    SellerRepository sellerRepository;

    SellerDtoFactory sellerDtoFactory;

    public static final String AUTHORIZE    = "api/sellers/authorize";

    public static final String REGISTRATION = "api/sellers/registration";

    /*
    @Transactional
    @GetMapping(AUTHORIZE)
    public ResponseEntity<SellerDto> login(@RequestParam String phone_number,
                                           @RequestParam String password) throws ChangeSetPersister.NotFoundException {




        // FIX: Почему-то не работает лямбда выражение, пофиксить
        var sellerEntity = sellerRepository
                .findTopByPhoneNumberAndPassword(phone_number, password)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);  // <= вон тут

        return ResponseEntity.ok(sellerDtoFactory.createSellerDto(sellerEntity));
    }
     //*/

    //*
    @Transactional
    @GetMapping(AUTHORIZE)
    public ResponseEntity<SellerDto> login(@RequestParam String phone_number,
                                           @RequestParam String password) throws ChangeSetPersister.NotFoundException {

        //я попробовал if))) хотя пока я искал как сделать это через if понял как работает твой замудреный orElseThrow()
        // а еще понял что мои пыпытки выглядят жалко, относительно твоей 1-ой строчки [eqyz
        //*
        var sellerEntityOptional = sellerRepository
                .findTopByPhoneNumberAndPassword(phone_number, password);

        if (!sellerEntityOptional.isPresent()){
            throw new ChangeSetPersister.NotFoundException();
        }

        var sellerEntity = sellerEntityOptional.get();

        return ResponseEntity.ok(sellerDtoFactory.createSellerDto(sellerEntity));
    }
    //*/

    @Transactional
    @PostMapping(REGISTRATION)
    public ResponseEntity<SellerDto> registration(@RequestParam String phone_number,
                                                @RequestParam String password,
                                                @RequestParam String fio,
                                                @RequestParam Integer age) throws ChangeSetPersister.NotFoundException {

        // FIX: Почему-то не работает лямбда выражение, пофиксить
        var sellerEntity = sellerRepository
                .findTopByPhoneNumberAndPassword(phone_number, password)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);  // <= вон тут

        return ResponseEntity.ok(sellerDtoFactory.createSellerDto(sellerEntity));
    }
}
