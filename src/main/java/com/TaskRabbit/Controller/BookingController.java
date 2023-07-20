package com.TaskRabbit.Controller;

import com.TaskRabbit.Payload.CustomerRequest;
import com.TaskRabbit.Service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private CustomerService customerService;

    public BookingController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //http://localhost:8080/bookings
    @PostMapping
    public ResponseEntity<String> bookTask(@RequestBody CustomerRequest customerRequest) {
        customerService.bookTask(customerRequest);
        return ResponseEntity.ok("Task booked successfully!");
    }

    //http://localhost:8080/bookings/{customerId}/cancel
    @PostMapping("/{customerId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long customerId) {
        try {
            customerService.cancelBooking(customerId);
            return ResponseEntity.ok("Booking cancelled successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Booking is already cancelled.");
        }
    }

    //http://localhost:8080/bookings/{customerId}/delete
    @DeleteMapping("/{customerId}/delete")
    public ResponseEntity<String> deleteBookingById(@PathVariable Long customerId) {
        customerService.deleteBookingById(customerId);
        return new ResponseEntity<>("Booking is Deleted", HttpStatus.OK);
    }
}
