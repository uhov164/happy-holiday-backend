package happy.holiday.server.factory;

import happy.holiday.server.dto.SellerDto;
import happy.holiday.server.entity.SellerEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component 
public class SellerDtoFactory {

    @Transactional
    public SellerDto createSellerDto(SellerEntity sellerEntity){

        var phoneNumber = sellerEntity.getPhoneNumber(); 
        var fio = sellerEntity.getFio();
        var age = sellerEntity.getAge();

        return new SellerDto(phoneNumber, fio, age);
    }
}
