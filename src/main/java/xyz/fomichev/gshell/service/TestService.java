package xyz.fomichev.gshell.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TestService.class);

    public void testMethod(String message) {
        log.info("My message is " + message);
    }

    public void testException() {
        throw new IllegalArgumentException("Hello exception");
    }

}
