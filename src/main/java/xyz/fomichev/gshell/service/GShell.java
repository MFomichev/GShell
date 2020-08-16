package xyz.fomichev.gshell.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Component
public class GShell {

    private final ScriptEngineManager scriptEngineManager;
    private final ApplicationContext context;

    public GShell(@Lazy ApplicationContext context) {
        this.context = context;
        this.scriptEngineManager = new ScriptEngineManager();
    }

    @PostConstruct
    void init() {
        for (String beanName : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(beanName);
            this.scriptEngineManager.put(beanName, bean);
        }
    }

    public String execute(String command) {
        var outputBytes = new ByteArrayOutputStream();
        var outWriter = new PrintWriter(outputBytes, true);
        var engine = scriptEngineManager.getEngineByExtension("groovy");
        var context = engine.getContext();
        context.setWriter(outWriter);
        context.setErrorWriter(outWriter);
        try {
            var result = engine.eval(command);
            var output = outputBytes.toString();
            if (result == null) {
                return output;
            }
            String resultAsString = result.toString();
            if (output.isEmpty()) {
                return resultAsString;
            }
            if (output.endsWith(System.lineSeparator())) {
                return output + resultAsString;
            }
            return output + System.lineSeparator() + resultAsString;
        } catch (Exception e) {
            e.printStackTrace(outWriter);
            return outputBytes.toString();
        }
    }
}
