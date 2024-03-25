package com.advance.programming.labappointment.service;

import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.Payment;
import com.advance.programming.labappointment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment makePayment(Appointment appointment, BigDecimal amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(amount);
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(paymentMethod);

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByAppointment(Appointment appointment) {
        return paymentRepository.findByAppointment(appointment);
    }
}
