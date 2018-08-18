package net.merc.bandwidth.demo.rest;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Api(value="/bandwdith/demo", description="Demos using the Bandwidth APIs")
public class DemoController {
    private final static Logger LOG = LoggerFactory.getLogger(DemoController.class);

    private final static String THANK_YOU = "Thank you!";
    private final static String THANK_YOU_FOR_MESSAGE = "Thank you for your message!";
    private final static String THANK_YOU_FOR_CALLING = "Thank you for calling! Trying now...";

    private final static String CANNED_MESSAGE = "Someone with this number tried the demo.";

    private final static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private final static String regionCode = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(1);

    public DemoController() {
        LOG.info("Instantiated");
    }

    @GetMapping("/bandwidth/demo")
    public String demoForm(Model model) {
        model.addAttribute("demoform", new DemoForm());
        return "demo";
    }

    @PostMapping("/bandwidth/demo/sendSMS")
    public String sendSms(@ModelAttribute DemoForm demoform, Model model) {
        LOG.info("Got sourceDN: {}, customMessage: '{}'", demoform.getSourceDN(), demoform.getCustomMessage());
        if (demoform.getCustomMessage() == null || demoform.getCustomMessage().isEmpty()) {
            demoform.setCustomMessage(CANNED_MESSAGE);
        }

        model.addAttribute("demoform", demoform);

        if (!validDN(demoform.getSourceDN())) {
            return "resultBadDN";
        }

        demoform.setSourceDN(normalizeDN(demoform.getSourceDN()));

        return "resultSMS";
    }

    @PostMapping("/bandwidth/demo/makeCall")
    public String makeCall(@ModelAttribute DemoForm demoform, Model model) {
        LOG.info("Got sourceDN: {}, customMessage: '{}'", demoform.getSourceDN(), demoform.getCustomMessage());

        model.addAttribute("demoform", demoform);

        if (!validDN(demoform.getSourceDN())) {
            return "resultBadDN";
        }

        demoform.setSourceDN(normalizeDN(demoform.getSourceDN()));

        return "resultCalling";
    }

    private static boolean validDN(final String dn) {
        if (dn == null || dn.isEmpty()) {
            LOG.debug("DN was null or empty");
            return false;
        }

        try {
            PhoneNumber phoneNumber = phoneUtil.parse(new StringBuffer(dn), regionCode);
            return phoneUtil.isValidNumber(phoneNumber);
        } catch (Exception e){
            LOG.error("Got exception parsing DN={}", dn, e);
            return false;
        }

    }

    private static String normalizeDN(final String dn) {
        try {
            PhoneNumber phoneNumber = phoneUtil.parse(new StringBuffer(dn), regionCode);
            return phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception e) {
            LOG.error("Got exception normalizing DN={}", dn, e);
            return dn;
        }
    }

}
