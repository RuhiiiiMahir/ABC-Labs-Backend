package com.advance.programming.labappointment.controller;

import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.Payment;
import com.advance.programming.labappointment.model.User;
import com.advance.programming.labappointment.repository.AppointmentRepo;
import com.advance.programming.labappointment.repository.PaymentRepository;
import com.advance.programming.labappointment.repository.UserRepo;
import com.advance.programming.labappointment.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepo appointmentRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository, AppointmentRepo appointmentRepo, UserRepo userRepo, EmailService emailService) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);

        Appointment appointment = appointmentRepo.getReferenceById(payment.getAppointment().getAppointmentID());

        User user = userRepo.getUserById(appointment.getUser().getId());

        String email = user.getEmail();
        System.out.println(email);
        // Create PDF invoice
        byte[] invoicePdf = createInvoicePdf(payment, user);

        // Send confirmation email with invoice PDF attached
        sendConfirmationEmailWithAttachment(email, "Payment Successfully Done!!", "Find your slip in attachments." + "\n", invoicePdf);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Payment> getPaymentByAppointmentId(@PathVariable Long appointmentId) {
        Payment payment = paymentRepository.findByAppointment_AppointmentID(appointmentId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private byte[] createInvoicePdf(Payment payment, User user) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.COURIER, 10);

                // Start adding content
                contentStream.beginText();

                // Set starting position
                float startX = 10;
                float startY = 775;

                // Address section
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("123 Galle Road");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Colombo, Sri Lanka");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("ABC-Lab is committed to delivering top-tier healthcare provisions.");
                contentStream.newLineAtOffset(20, -40);

                // Invoice title section
                contentStream.setFont(PDType1Font.COURIER_BOLD, 24);
                contentStream.setNonStrokingColor(0, 255, 0);
                contentStream.newLineAtOffset(10, -30);
                contentStream.showText("Payment Receipt - " + payment.getPaymentId());
                contentStream.newLineAtOffset(0, -30);

                // Content details section
                contentStream.setFont(PDType1Font.COURIER, 20);
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("Dear Sir/Madam,");
                contentStream.newLineAtOffset(0, -40);
                contentStream.showText("Your appointment payment has been successfully processed.");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Thank you for your payment.");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Amount: Rs " + payment.getAmount());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Payment Method : " + payment.getPaymentMethod());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Paid By: " + user.getFullname());

                // Signatory section
                contentStream.newLineAtOffset(0, -300);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("For any queries, please contact us at:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.COURIER_OBLIQUE, 12);
                contentStream.showText("ABC Laboratory PVT LTD");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: info@abclabs.lk");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Phone: +94 11 2345678");

                // End text
                contentStream.endText();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void sendConfirmationEmailWithAttachment(String to, String subject, String body, byte[] attachment) {
        emailService.sendConfirmationEmailWithAttachment(to, subject, body, attachment, "Invoice");
    }

}
