package com.TaskRabbit.Service.ServiceImpl;

import com.TaskRabbit.Entity.Customer;
import com.TaskRabbit.Entity.Tasker;
import com.TaskRabbit.Payload.CustomerRequest;
import com.TaskRabbit.Repository.CustomerRepository;
import com.TaskRabbit.Repository.TaskerRepository;
import com.TaskRabbit.Service.CustomerService;
import com.TaskRabbit.Service.MailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private TaskerRepository taskerRepository;
    private CustomerRepository customerRepository;
    private MailService mailService;

    public CustomerServiceImpl(TaskerRepository taskerRepository, CustomerRepository customerRepository,MailService mailService) {
        this.taskerRepository = taskerRepository;
        this.customerRepository = customerRepository;
        this.mailService=mailService;
    }

    @Override
    public void bookTask(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setAddress(customerRequest.getAddress());
        customer.setTime(customerRequest.getTime());
        customer.setDate(customerRequest.getDate());
        customer.setTaskDescription(customerRequest.getTaskDescription());
        customer.setEmail(customerRequest.getEmail());
        customer.setMobile(customerRequest.getMobile());

        Tasker selectedTasker = taskerRepository.findById(customerRequest.getTaskerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tasker ID"));
        customer.setTasker(selectedTasker);

        customerRepository.save(customer);
        String subject = "Task Booking Confirmation";
        String body = "Your task has been booked successfully!\n\n";
        body += "Task Details:\n";
        body += "Date: " + customerRequest.getDate() + "\n";
        body += "Time: " + customerRequest.getTime() + "\n";
        body += "Task Description: " + customerRequest.getTaskDescription() + "\n";
        body += "Address: " + customerRequest.getAddress() + "\n";
        body += "Email Address: " + customerRequest.getEmail() + "\n";
        body += "Mobile: " + customerRequest.getMobile() + "\n";
        sendEmailToCustomer(customerRequest.getEmail(), subject, body);

        String taskerSubject = "New Task Booking";
        String taskerBody = "A new task has been booked by a customer!\n\n";
        taskerBody += "Task Details:\n";
        taskerBody += "Date: " + customerRequest.getDate() + "\n";
        taskerBody += "Time: " + customerRequest.getTime() + "\n";
        taskerBody += "Task Description: " + customerRequest.getTaskDescription() + "\n";
        taskerBody += "Address: " + customerRequest.getAddress() + "\n";
        taskerBody += "Customer Email Address: " + customerRequest.getEmail() + "\n";
        taskerBody += "Customer Mobile: " + customerRequest.getMobile() + "\n";

        // Send the email to the Tasker using Tasker's email address
        sendEmailToCustomer(selectedTasker.getEmail(), taskerSubject, taskerBody);
    }

    @Override
    public void cancelBooking(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID"));

        if (!customer.isCancelled()) {
            customer.setCancelled(true);
            customerRepository.save(customer);
        } else {
            throw new IllegalStateException("Booking is already cancelled.");
        }
        String subject = "Task Cancellation Confirmation";
        String body = "Your task has been cancelled!\n\n";
        body += "Task Details:\n";
        body += "Date: " + customer.getDate() + "\n";
        body += "Time: " + customer.getTime() + "\n";
        body += "Task Description: " + customer.getTaskDescription() + "\n";
        body += "Address: " + customer.getAddress() + "\n";
        body += "Email Address: " + customer.getEmail() + "\n";
        body += "Mobile: " + customer.getMobile() + "\n";
        sendEmailToCustomer(customer.getEmail(), subject, body);

        Tasker tasker = customer.getTasker();
        if (tasker != null) {
            String taskerSubject = "Task Cancellation";
            String taskerBody = "A task assigned to you has been cancelled by the customer.\n\n";
            taskerBody += "Cancelled Task Details:\n";
            taskerBody += "Date: " + customer.getDate() + "\n";
            taskerBody += "Time: " + customer.getTime() + "\n";
            taskerBody += "Task Description: " + customer.getTaskDescription() + "\n";
            taskerBody += "Customer Email Address: " + customer.getEmail() + "\n";
            taskerBody += "Customer Mobile: " + customer.getMobile() + "\n";

            // Send the email to the Tasker using Tasker's email address
            sendEmailToCustomer(tasker.getEmail(), taskerSubject, taskerBody);

        }
    }

    @Override
    public void sendEmailToCustomer(String to, String subject, String body) {
        mailService.sendEmail(to, subject, body);
    }

    @Override
    public void deleteBookingById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID"));
        customerRepository.deleteById(customerId);
    }

}
