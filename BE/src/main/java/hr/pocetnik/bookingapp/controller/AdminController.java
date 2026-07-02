package hr.pocetnik.bookingapp.controller;

import hr.pocetnik.bookingapp.dto.listing.ListingResponse;
import hr.pocetnik.bookingapp.model.SellerDataEntity;
import hr.pocetnik.bookingapp.model.SellerRequestEntity;
import hr.pocetnik.bookingapp.model.UserEntity;
import hr.pocetnik.bookingapp.repository.UserRepository;
import hr.pocetnik.bookingapp.service.ListingService;
import hr.pocetnik.bookingapp.service.SellerRequestService;
import hr.pocetnik.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final SellerRequestService sellerRequestService;
    private final ListingService listingService;

    @Autowired
    public AdminController(
            UserRepository userRepository,
            UserService userService,
            SellerRequestService sellerRequestService,
            ListingService listingService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.sellerRequestService = sellerRequestService;
        this.listingService = listingService;
    }

    @GetMapping("/users")
    public List<Map<String, String>> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapUser)
                .toList();
    }

    @PostMapping("/users/{userId}")
    public Map<String, String> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody Map<String, String> userMap) {
        UserEntity updatedUser = userService.updateUserByAdmin(
                userId,
                userMap.get("name"),
                userMap.get("surname"),
                userMap.get("email"),
                userMap.get("phoneNumber"),
                userMap.get("role"));

        return mapUser(updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserByAdmin(userId);
    }

    @GetMapping("/seller-requests")
    public List<Map<String, String>> getSellerRequests() {
        return sellerRequestService.getAllSellerRequests()
                .stream()
                .map(this::mapSellerRequest)
                .toList();
    }

    @PostMapping("/seller-requests/{requestId}/approve")
    public Map<String, String> approveSellerRequest(
            @PathVariable("requestId") Long requestId) {
        return mapSellerRequest(sellerRequestService.approveSellerRequest(requestId));
    }

    @PostMapping("/seller-requests/{requestId}/reject")
    public Map<String, String> rejectSellerRequest(
            @PathVariable("requestId") Long requestId) {
        return mapSellerRequest(sellerRequestService.rejectSellerRequest(requestId));
    }

    @GetMapping("/listings")
    public List<ListingResponse> getAllListings() {
        return listingService.getAllListings();
    }

    @PostMapping("/listings/{listingId}/approve")
    public ListingResponse approveListing(
            @PathVariable("listingId") Long listingId) {
        return listingService.approveListing(listingId);
    }

    @PostMapping("/listings/{listingId}/reject")
    public ListingResponse rejectListing(
            @PathVariable("listingId") Long listingId) {
        return listingService.rejectListing(listingId);
    }

    private Map<String, String> mapUser(UserEntity user) {
        return Map.of(
                "id", user.getId().toString(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "phoneNumber", user.getPhoneNumber(),
                "role", user.getRole().name());
    }

    private Map<String, String> mapSellerRequest(SellerRequestEntity request) {
        return Map.ofEntries(
                Map.entry("id", String.valueOf(request.getId())),
                Map.entry("requestText", valueOrEmpty(request.getRequestText())),
                Map.entry("status", request.getStatus().name()),
                Map.entry("createdAt", request.getCreatedAt() != null ? request.getCreatedAt().toString() : ""),

                Map.entry("userName", valueOrEmpty(request.getUser().getName())),
                Map.entry("userSurname", valueOrEmpty(request.getUser().getSurname())),
                Map.entry("userEmail", valueOrEmpty(request.getUser().getEmail())),

                Map.entry("businessName", valueOrEmpty(request.getBusinessName())),
                Map.entry("oib", valueOrEmpty(request.getOib())),
                Map.entry("iban", valueOrEmpty(request.getIban())),
                Map.entry("billingAddress", valueOrEmpty(request.getBillingAddress())));
    }

    private String valueOrEmpty(String value) {
        return value != null ? value : "";
    }
}