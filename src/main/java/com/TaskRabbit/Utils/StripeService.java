package com.TaskRabbit.Utils;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    private final String secretKey = "sk_test_51NGQOvSBUiXb0mZEZDmXsff22eY8JcRhwis3iS6vHl45nbGoCXo0Jr5NamH0fVtgdcreo1Dyy1LI1MHqIA6SorPZ0092fJ9Nr5";

    public void initStripe() {
        Stripe.apiKey = secretKey;
    }

    public String createPaymentIntent(int amountInCents, String currency) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount((long) amountInCents)
                        .setCurrency(currency)
                        .build()
        );
        return paymentIntent.getClientSecret();
    }
}

