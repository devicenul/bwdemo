package net.merc.bandwidth.demo.rest;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bandwidth/demo")
@Api(value="/bandwdith/demo", description="Demos using the Bandwidth APIs")
public class DemoController {
    private final static Logger LOG = LoggerFactory.getLogger(DemoController.class);

    private final static String THANK_YOU = "Thank you!";
    private final static String THANK_YOU_FOR_MESSAGE = "Thank you for your message!";
    private final static String THANK_YOU_FOR_CALLING = "Thank you for calling! Trying now...";

    public DemoController() {
        LOG.info("Instantiated");
    }

    @RequestMapping(value="/sendSMS/{sourceDN}", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
    //@RequestMapping(value="/sendSMS/{sourceDN}", method= RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody String sendSms(final @PathVariable("sourceDN") String sourceDN) {
        return THANK_YOU;
    }

    @RequestMapping(value="/sendSMS/{sourceDN}", method=RequestMethod.POST, consumes=MediaType.TEXT_PLAIN_VALUE, produces=MediaType.TEXT_PLAIN_VALUE)
    //@RequestMapping(value="/sendSMS/{sourceDN}", method= RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody String sendCustomSms(final @PathVariable("sourceDN") String sourceDN, final @RequestBody String message) {
        return THANK_YOU_FOR_MESSAGE;
    }


    @RequestMapping(value="/makeCall/{sourceDN", method=RequestMethod.GET, produces=MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String makeCall(final @PathVariable("sourceDN") String sourceDN) {
        return THANK_YOU_FOR_CALLING;
    }

}
