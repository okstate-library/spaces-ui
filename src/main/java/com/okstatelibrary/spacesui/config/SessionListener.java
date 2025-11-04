package com.okstatelibrary.spacesui.config;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class SessionListener implements HttpSessionListener {

	// AtomicInteger ensures thread-safety
    private static final AtomicInteger activeSessions = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions.incrementAndGet();
        System.out.println("Session created: " + se.getSession().getId());
        System.out.println("Active sessions: " + activeSessions.get());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions.decrementAndGet();
        System.out.println("Session destroyed: " + se.getSession().getId());
        System.out.println("Active sessions: " + activeSessions.get());
    }

    // Provide a static getter for other classes (like controllers)
    public static int getActiveSessions() {
        return activeSessions.get();
    }
}
