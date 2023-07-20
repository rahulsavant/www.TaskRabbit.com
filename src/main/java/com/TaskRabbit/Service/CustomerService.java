package com.TaskRabbit.Service;

import com.TaskRabbit.Payload.CustomerRequest;

public interface CustomerService {
    void bookTask(CustomerRequest customerRequest);

    void cancelBooking(Long customerId);

    void sendEmailToCustomer(String to, String subject, String body);

    void deleteBookingById(Long customerId);
}
