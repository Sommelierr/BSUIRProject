package by.sommelierr.controllers;

import by.sommelierr.models.Data;
import by.sommelierr.models.Region;
import by.sommelierr.models.Sensor;
import by.sommelierr.models.User;
import by.sommelierr.repository.DataRepository;
import by.sommelierr.repository.RegionRepository;
import by.sommelierr.repository.SensorRepository;
import by.sommelierr.repository.UserRepository;
import by.sommelierr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    UserService userService;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    SensorRepository sensorRepository;

    List<Data> vdata = new ArrayList<>();

    List<Sensor> sensorList = new ArrayList<>();

    @GetMapping("/users")
    public String users(Model model) {
        if (userService.isNotExecutable()) return "redirect:/login";
        userService.setLoginDate();
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/error")
    public String error() {
        if (userService.isNotExecutable()) return "redirect:/login";
        return "/error";
    }

    @PostMapping("/users")
    public String users(@RequestParam List<Long> checkId, @RequestParam String action) {
        if (userService.isNotExecutable()) return "redirect:/login";
        userService.doAction(action, checkId);
        if (userService.isNotExecutable()) return "redirect:/login";
        return "redirect:/users";
    }

    @GetMapping("/data")
    public String data(Model model) {
        vdata = dataRepository.findAll();
        List<Integer> ids = new ArrayList<>();
        List<Integer> tms = new ArrayList<>();
        for (Data data : vdata) {
            ids.add(data.getId());
            tms.add(data.getTemperature());
        }
        model.addAttribute("data", vdata);
        model.addAttribute("ids", ids);
        model.addAttribute("tms", tms);
        return "/data";
    }

    @PostMapping("/data")
    public String data(@RequestParam(value = "proc", required = false) String proc, @RequestParam(value = "but", required = false) String but, Model model) throws ParseException {
        System.out.println(proc == null);
        List<Data> datas = dataRepository.findAll();
        if (proc != null) {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date date = format.parse(proc);
            System.out.println(date.toString());

            vdata.clear();
            for (Data data : datas) {
                System.out.println(data.getRegistrationData().toString());
                if (data.getRegistrationData().compareTo(date) == 0) {
                    vdata.add(data);
                }
            }
        }
        if (but != null) {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "blazeing333@gmail.com";//
            final String password = "sharl-de-gol-207";
            try {
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // -- Create a new message --
                Message msg = new MimeMessage(session);

                // -- Set the FROM and TO fields --
                msg.setFrom(new InternetAddress("blazeing333@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("blaze_ing@mail.ru", false));
                msg.setSubject(".!.");
                msg.setText(vdata.toString());
                msg.setSentDate(new Date());
                Transport.send(msg);
                System.out.println("Message sent.");
            } catch (MessagingException e) {
                System.out.println("Erreur d'envoi, cause: " + e);

            }

        }

        model.addAttribute("data", vdata);
        return "/data";
    }

    @GetMapping("/location")
    public String location(Model model) {
        List<Sensor> sensors = sensorRepository.findAll();
        model.addAttribute("data", sensors);
        return "/location";
    }

    @PostMapping("/location")
    public String location(@RequestParam(value = "proc", required = false) String proc, @RequestParam(value = "but", required = false) String but, Model model){
        List<Sensor> sensors = sensorRepository.findAll();
        if(proc != null) {
            sensorList.clear();
            for (Sensor sens : sensors) {
                if (sens.getRegion().getId() == Integer.parseInt(proc)) sensorList.add(sens);
                System.out.println(sens.getRegion().getId() == Integer.parseInt(proc));
            }
        model.addAttribute("data", sensorList);
        }
        if(but != null){
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "blazeing333@gmail.com";//
            final String password = "sharl-de-gol-207";
            try {
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // -- Create a new message --
                Message msg = new MimeMessage(session);

                // -- Set the FROM and TO fields --
                msg.setFrom(new InternetAddress("blazeing333@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("blaze_ing@mail.ru", false));
                msg.setSubject(".!.");
                msg.setText(sensorList.toString());
                msg.setSentDate(new Date());
                Transport.send(msg);
                System.out.println("Message sent.");
            } catch (MessagingException e) {
                System.out.println("Erreur d'envoi, cause: " + e);

            }
        }
        model.addAttribute("data", sensorList);
        return "/location";
    }

    @GetMapping("/2")
    public String data1(Model model) {
        vdata = dataRepository.findAll();
        model.addAttribute("data", vdata);
        return "/data1";
    }

    @PostMapping("/2")
    public String data1Post(Model model, @RequestParam(value = "fdate", required = false) String first, @RequestParam(value = "ldate", required = false) String last, @RequestParam(value = "but", required = false) String but) throws ParseException {
        List<Data> datas = dataRepository.findAll();
        if (first != null && last != null) {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date firstDate = format.parse(first);
            Date lastDate = format.parse(last);
            vdata.clear();
            for (Data data : datas) {
                if (data.getRegistrationData().compareTo(firstDate) == 1 && data.getRegistrationData().compareTo(lastDate) == -1) {
                    vdata.add(data);
                    System.out.println();
                }
            }
        }
        if (but != null) {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "blazeing333@gmail.com";//
            final String password = "sharl-de-gol-207";
            try {
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // -- Create a new message --
                Message msg = new MimeMessage(session);

                // -- Set the FROM and TO fields --
                msg.setFrom(new InternetAddress("blazeing333@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("blaze_ing@mail.ru", false));
                msg.setSubject(".!.");
                msg.setText(vdata.toString());
                msg.setSentDate(new Date());
                Transport.send(msg);
                System.out.println("Message sent.");
            } catch (MessagingException e) {
                System.out.println("Erreur d'envoi, cause: " + e);

            }

        }

        model.addAttribute("data", vdata);
        return "/data1";
    }

    @GetMapping("/3")
    public String data3(Model model) {
        List<Data> datas = dataRepository.findAll();
        vdata.clear();
        int sr = 0;
        for (Data data : datas) {
            sr += data.getTemperature();
        }
        sr /= datas.size();
        for (Data data : datas) {
            data.setTemperature(data.getTemperature() - sr);
            vdata.add(data);
        }
        model.addAttribute("data", vdata);
        return "/data2";
    }

    @PostMapping("/3")
    public String data3Post(@RequestParam(value = "but", required = false) String but, Model model) {
        if (but != null) {
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            final String username = "blazeing333@gmail.com";//
            final String password = "sharl-de-gol-207";
            try {
                Session session = Session.getDefaultInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // -- Create a new message --
                Message msg = new MimeMessage(session);

                // -- Set the FROM and TO fields --
                msg.setFrom(new InternetAddress("blazeing333@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("blaze_ing@mail.ru", false));
                msg.setSubject(".!.");
                msg.setText(vdata.toString());
                msg.setSentDate(new Date());
                Transport.send(msg);
                System.out.println("Message sent.");
            } catch (MessagingException e) {
                System.out.println("Erreur d'envoi, cause: " + e);

            }
        }
        model.addAttribute("data", vdata);
        return "/data2";
    }
}