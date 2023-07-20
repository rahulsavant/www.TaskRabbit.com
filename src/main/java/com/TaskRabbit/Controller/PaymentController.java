package com.TaskRabbit.Controller;


import com.TaskRabbit.Utils.EmailService;
import com.TaskRabbit.Utils.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private StripeService stripeService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestParam("amount") int amountInCents,
                                                 @RequestParam("currency") String currency,
                                                 @RequestParam("email") String email) {
        try {
            stripeService.initStripe();
            String clientSecret = stripeService.createPaymentIntent(amountInCents, currency);

            // Send payment success email
            String emailSubject = "Payment Successful";
            String emailText = "Your payment was successful.";
            emailService.sendEmail(email, emailSubject, emailText);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("clientSecret", clientSecret);
            return ResponseEntity.ok().body(responseMap);
        } catch (StripeException e) {
            // Handle payment error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "PaymentIntent creation failed"));
        }
    }
}









