package io.cantu;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final String template = "Hellos, %s!";

    @RequestMapping(method = RequestMethod.GET)
    public String hello(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }
}
