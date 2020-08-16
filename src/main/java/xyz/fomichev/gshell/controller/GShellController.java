package xyz.fomichev.gshell.controller;

import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.fomichev.gshell.service.GShell;

@RestController
public class GShellController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GShellController.class);

    private final GShell gShell;

    public GShellController(@Lazy GShell gShell) {
        this.gShell = gShell;
    }

    @PostMapping("/")
    public void test() {
        log.info(gShell.execute("testService.testException()"));
        log.info("Start test " + gShell.execute("println('Hello println')"));
        log.info("Start test " + gShell.execute("testService.testMethod(\"Hello World\")"));
        log.info("Start test 1" + gShell.execute("testService.testMethod()"));
        log.info("Start test 1" + gShell.execute("2 + 3"));
    }

}
