package kgu.sw.team1.booksys.web.service;

import kgu.sw.team1.booksys.domain.Customer;
import kgu.sw.team1.booksys.domain.Reservation;
import kgu.sw.team1.booksys.domain.ReservationNotifyQueue;
import kgu.sw.team1.booksys.repository.CustomerRepository;
import kgu.sw.team1.booksys.repository.ReservationNotifyQueueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    @Value("classpath:/templates/email/signUpTemplates.html")
    private File signUp;
    @Value("classpath:/templates/email/reservationTemplates.html")
    private File reservation;
    @Value("classpath:/templates/email/notifyTemplates.html")
    private File notify;
    @Value("classpath:/static/assets/email_1.png")
    private File email_1;

    private final JavaMailSender emailSender;
    private final ReservationNotifyQueueRepository notifyQueueRepository;
    private final CustomerRepository customerRepository;
    private final ReservationService reservationService;

    public EmailService(JavaMailSender emailSender, ReservationNotifyQueueRepository notifyQueueRepository, CustomerRepository customerRepository, ReservationService reservationService) {
        this.emailSender = emailSender;
        this.notifyQueueRepository = notifyQueueRepository;
        this.customerRepository = customerRepository;
        this.reservationService = reservationService;
    }

    /**
     * 메일 전송 메소드
     * @param type
     * 1 : signUp
     * 2 : reservation
     * 3 : notify
     */
    @Async
    public void sendMessage(Customer customer, Integer type) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(customer.getEmail());

            if (type == 1) {
                helper.setText(readFile(signUp), true);
                helper.setSubject("[Food & Forest] 회원가입 완료 안내");
            } else if (type == 2) {
                helper.setText(readFile(reservation), true);
                helper.setSubject("[Food & Forest] 사전예약 완료 안내");
            } else {
                helper.setText(readFile(notify), true);
                helper.setSubject("[Food & Forest] 사전예약 임박 알림");
            }
            helper.setText(readFile(signUp), true);
            helper.addInline("email_1", email_1);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

        emailSender.send(message);
    }

    /**
     * 이메일 html 템플릿 파싱 메소드
     */
    public String readFile(File file) {
        StringBuilder string = new StringBuilder();
        Charset charset = StandardCharsets.UTF_8;
        try {
            for (String line : Files.readAllLines(file.toPath(), charset)) {
                string.append(line);
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return string.toString();
    }

    /**
     * 예약 사전 알림 스케쥴링
     * 매일 12:00:00 에 알림 전송
     */
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    public void notifyScheduleTask() {
        List<ReservationNotifyQueue> queues = notifyQueueRepository.findAllByDate(LocalDate.now());
        for (ReservationNotifyQueue queue : queues) {
            Customer customer = customerRepository.findById(queue.getCustomerOid()).get();
            sendMessage(customer, 3);
            reservationService.deleteNotifyQueue(queue.getReservationOid());
        }
    }
}
