package com.abc.controller;

import com.abc.entity.Taikhoan;
import com.abc.model.EmailRequest;
import com.abc.repository.TaikhoanRepository;
import com.abc.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "Mail APIs")
@CrossOrigin
@RequestMapping("/mail")
@RestController
public class MailController {

//    @Autowired
//    private MailService emailService;
    @Autowired
    private TaikhoanRepository tkRepo;

    //this api send simple email
    @PostMapping("/sendingemail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
        System.out.println(request);
        MailService mailService = new MailService();
        String mail = request.getTo();
        Taikhoan tk = new Taikhoan();
        tk = tkRepo.findTaikhoanByEmail(mail);
//        String content = tk.getUsername() + tk.getPassword();
        if(tk == null || tk.getUsername() == null || tk.getUsername().compareToIgnoreCase("") == 0) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot find user!");
        String temp = "Medical Ecommerce, \nHi there, \nWe've sent an email to the address provided, your account here: \n\n Your Username: " + tk.getUsername() + "\n Password: "+tk.getPassword();
        System.out.println(temp);
        boolean result = mailService.sendEmail(request.getSubject(), temp, request.getTo());
        if (result) {
            return ResponseEntity.ok("Email Properly Sent Successfully... ");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
        }
    }

//    @PostMapping("/sendingemail")
//    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {
//        System.out.println(request);
//        MailService mailService = new MailService();
//        String mail = request.getTo();
//        Taikhoan tk = new Taikhoan();
//        tk = tkRepo.findTaikhoanByEmail(mail);
////        String content = tk.getUsername() + tk.getPassword();
//        if(tk == null || tk.getUsername() == null || tk.getUsername().compareToIgnoreCase("") == 0) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
//        String temp = "Your Username: " + tk.getUsername() + " and Password: "+tk.getPassword();
//        System.out.println(temp);
//        boolean result = mailService.sendEmail(request.getSubject(), temp, request.getTo());
//        if (result) {
//            return ResponseEntity.ok("Email Properly Sent Successfully... ");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
//        }
//    }

    @PostMapping("/sendingemail/nv")
    public ResponseEntity<?> sendEmailNV(@RequestBody EmailRequest request) {
        System.out.println(request);
        MailService mailService = new MailService();
        String mail = request.getTo();
        Taikhoan tk = new Taikhoan();
        tk = tkRepo.findTaikhoanNVByEmail(mail);
        if(tk == null || tk.getUsername() == null || tk.getUsername().compareToIgnoreCase("") == 0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot find user!");
        String temp = "Medical Ecommerce, \nHi there, \nWe've sent an email to the address provided, your account here: \n\n Your Username: " + tk.getUsername() + "\n Password: "+tk.getPassword();
        System.out.println(temp);
        boolean result = mailService.sendEmail(request.getSubject(), temp, request.getTo());
        if (result) {
            return ResponseEntity.ok("Email Properly Sent Successfully... ");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
        }
    }
}