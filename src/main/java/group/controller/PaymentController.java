package group.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @PostMapping("/payment")
    public Map<String, Boolean> processPayment(@RequestBody Map<String, String> payload) throws StripeException {
        String paymentMethodId = payload.get("paymentMethodId");
        String email = payload.get("email");
        String name = payload.get("name");
        double amountInDollars = Double.parseDouble(payload.get("amount"));

        long amountInCents = Math.round(amountInDollars * 100);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setPaymentMethod(paymentMethodId)
                .setReceiptEmail(email)
                .setDescription("Payment from " + name)
                .setConfirm(true)
                .setReturnUrl("https://dewvdtfd5m.execute-api.eu-north-1.amazonaws.com/dev/events")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", "succeeded".equals(paymentIntent.getStatus()));
        return response;

    }
}