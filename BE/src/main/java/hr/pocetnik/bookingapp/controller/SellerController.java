package hr.pocetnik.bookingapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.pocetnik.bookingapp.dto.seller.SellerDataResponse;
import hr.pocetnik.bookingapp.model.SellerDataEntity;
import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.UserRepository;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final UserRepository userRepository;

    public SellerController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public SellerDataResponse getMySellerData(Authentication authentication) {
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getSellerData() == null) {
            throw new RuntimeException("Seller data not found");
        }

        SellerDataEntity sellerData = user.getSellerData();

        return new SellerDataResponse(
                sellerData.getBusinessName(),
                sellerData.getOib(),
                sellerData.getIban(),
                sellerData.getBillingAddress());
    }
}