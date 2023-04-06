package happy.holiday.server.repository;

import happy.holiday.server.entity.SellerEntity;

import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Integer> {
    Optional<SellerEntity> findTopByPhoneNumberAndPassword(@NonNull String phoneNumber, @NonNull String password);
    Optional<SellerEntity> findTopByPhoneNumber(@NonNull String phoneNumber);
}
