package com.advance.programming.labappointment;

import com.advance.programming.labappointment.controller.ApiController;
import com.advance.programming.labappointment.controller.AppointmentController;
import com.advance.programming.labappointment.controller.PaymentController;
import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.Payment;
import com.advance.programming.labappointment.model.User;
import com.advance.programming.labappointment.repository.AppointmentRepo;
import com.advance.programming.labappointment.repository.PaymentRepository;
import com.advance.programming.labappointment.repository.UserRepo;
import com.advance.programming.labappointment.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class LabAppointmentApplicationTests {

	@Mock
	private PaymentRepository paymentRepository;
	@Mock
	private AppointmentRepo appointmentRepo;

	@Mock
	private UserRepo userRepo;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private PaymentController paymentController;
	@InjectMocks
	private AppointmentController appointmentController;

	@InjectMocks
	private ApiController apiController;
	@Test
	public void testGetAllAppointment() {
		// Mock data
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(1L);

		when(appointmentRepo.findAll()).thenReturn(Collections.singletonList(appointment));

		// Call the controller method
		List<Appointment> result = appointmentController.getAllAppointment();

		// Verify the result
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getAppointmentID());

		// Print results
		System.out.println("Number of appointments: " + result.size());
		System.out.println("First appointment ID: " + result.get(0).getAppointmentID());
	}

	@Test
	public void testGetPage() {
		// Call the controller method
		String result = apiController.getPage();

		// Verify the result
		assertEquals("Welcome Home!!", result);

		// Print result
		System.out.println("API Page content: " + result);
	}

	@Test
	public void testGetAppointmentsByUser() {
		// Mock data
		Long userId = 1L;
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(1L);

		when(userRepo.getUserById(userId)).thenReturn(new User());
		when(appointmentRepo.findByUser(any())).thenReturn(Collections.singletonList(appointment));

		// Call the controller method
		List<Appointment> result = appointmentController.getAppointmentsByUser(userId);

		// Verify the result
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getAppointmentID());
	}

	@Test
	public void testSendConfirmationEmail() {
		// Mock data
		Appointment appointment = new Appointment();
		appointment.setAppointmentID(1L);
		User user = new User();
		user.setEmail("test@example.com");

		doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

		// Call the controller method
		appointmentController.sendConfirmationEmail(user.getEmail(), "Subject", "Body");

		// Verify the email service is called
		verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
	}

	@Test
	public void testGetPaymentByAppointmentId_PaymentExists() {
		// Mock data
		Long appointmentId = 1L;
		Payment payment = new Payment();
		payment.setPaymentId(1L);

		when(paymentRepository.findByAppointment_AppointmentID(appointmentId)).thenReturn(payment);

		// Call the controller method
		ResponseEntity<Payment> responseEntity = paymentController.getPaymentByAppointmentId(appointmentId);

		// Verify the result
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(payment, responseEntity.getBody());
	}

	@Test
	public void testGetPaymentByAppointmentId_PaymentNotExists() {
		// Mock data
		Long appointmentId = 1L;

		when(paymentRepository.findByAppointment_AppointmentID(appointmentId)).thenReturn(null);

		// Call the controller method
		ResponseEntity<Payment> responseEntity = paymentController.getPaymentByAppointmentId(appointmentId);

		// Verify the result
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(null, responseEntity.getBody());
	}

	@Test
	public void testSendConfirmationEmailWithAttachment() {
		// Mock data
		String to = "test@example.com";
		String subject = "Subject";
		String body = "Body";
		byte[] attachment = "Attachment".getBytes();

		doNothing().when(emailService).sendConfirmationEmailWithAttachment(anyString(), anyString(), anyString(), any(byte[].class), anyString());

		// Call the controller method
		paymentController.sendConfirmationEmailWithAttachment(to, subject, body, attachment);

		// Verify the email service is called
		verify(emailService, times(1)).sendConfirmationEmailWithAttachment(anyString(), anyString(), anyString(), any(byte[].class), anyString());
	}


}