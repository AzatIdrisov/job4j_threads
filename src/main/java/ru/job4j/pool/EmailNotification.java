package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        StringBuilder subject = new StringBuilder()
                .append("Notification ")
                .append(user.getUserName())
                .append(" to email ")
                .append(user.getEmail());
        StringBuilder body = new StringBuilder()
                .append("Add a new event to ")
                .append(user.getUserName());
        send(subject.toString(), body.toString(), user.getEmail());
        System.out.println("Email have sent");
    }

    public void close() {
        this.pool.shutdown();
    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        User user = new User("Azat", "azat@example.com");
        pool.submit(() -> emailNotification.emailTo(user));
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
