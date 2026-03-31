# 🚀 Spring Boot 3rd Party API Integration

This project demonstrates how to integrate **Stripe Payment Gateway** and **Email Service using Gmail SMTP** in a Spring Boot application.

---

## 📌 Features

- 💳 Stripe Payment Integration (Checkout / Payment Intent)
- 📧 Email Sending via Gmail SMTP
- ⚙️ REST APIs built with Spring Boot
- 🧪 Easily testable via Postman or frontend

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Maven
- Stripe API
- Spring Boot Mail (JavaMailSender)
- Gmail SMTP

---

## 📂 Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── config/
 ├── dto/
 └── util/
```

---

## ⚙️ Configuration

### 🔑 Stripe Setup

1. Create an account: https://stripe.com  
2. Get your API keys from the dashboard  

Add to `application.properties`:

```
stripe.secret.key=your_stripe_secret_key
stripe.public.key=your_stripe_public_key
```

---

### 📧 Gmail SMTP Setup

Add to `application.properties`:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

⚠️ Use **App Password**, not your real Gmail password.

---

## ▶️ Running the Application

App will run at:

```
http://localhost:8080/signup

```

---

## 🔐 Environment Variables (Recommended)

```
STRIPE_SECRET_KEY=your_key
EMAIL_USERNAME=your_email
EMAIL_PASSWORD=your_password
```

---

## 📦 Dependencies

```
<dependency>
    <groupId>com.stripe</groupId>
    <artifactId>stripe-java</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

---

## 🚧 Future Improvements

- Stripe webhooks integration
- HTML email templates

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first.
