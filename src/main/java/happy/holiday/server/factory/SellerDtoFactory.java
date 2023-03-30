package happy.holiday.server.factory;

import happy.holiday.server.dto.SellerDto;
import happy.holiday.server.entity.SellerEntity;
import org.springframework.stereotype.Component;

@Component // такой же вопрос: зачем тут компонент и что он делает?
public class SellerDtoFactory {

    public SellerDto createSellerDto(SellerEntity sellerEntity){

        var phoneNumber = sellerEntity.getPhoneNumber(); // почему оно красное? точнее я догадываюсь - такого метода нет, но тогда вопрос на 2 коммента ниже
        var fio = sellerEntity.getFio();
        var age = sellerEntity.getAge();
        var listOfOrders = sellerEntity.getListOfOrders();
        //var listOfItems = sellerEntity.getListOfItems();
        var history = sellerEntity.getHistory();    // или тут getOrederHistory(); ?
                                                    // откуда ты вообще взял эти методы (getFio(), getAge() и т. д.)
                                                    // в классе ты их не прописывал
                                                    // да и вообще нужно ли тут listOfItems если его нет в SellerDto

        return new SellerDto(phoneNumber, fio, age, history, listOfOrders);
    }
}
