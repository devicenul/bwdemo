package net.merc.bandwidth.demo.rest;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import io.swagger.annotations.Api;
import net.merc.bandwidth.demo.bwclient.IBandwidthClient;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (c) 2018, rrjefferson@gmail.com under the MIT license.
 * See LICENSE.md for details.
 */

@Controller
@Api(value="/bandwidth/demo", description="Demos using the Bandwidth APIs")
public class DemoController {
    private final static Logger LOG = LoggerFactory.getLogger(DemoController.class);

    private final static String CANNED_MESSAGE = "%s: Someone with this number tried the demo.";
    private final static String CUSTOM_MESSAGE = "%s: %s";

    private final static PhoneNumberUtil PHONE_UTIL = PhoneNumberUtil.getInstance();
    private final static String REGION_CODE = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(1);

    private final IBandwidthClient bwClient;

    @Autowired
    public DemoController(final IBandwidthClient bwClient) {
        Validate.notNull(bwClient);

        this.bwClient = bwClient;

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

        if (!validDN(demoform.getSourceDN())) {
            return "resultBadDN";
        }

        demoform.setSourceDN(normalizeDN(demoform.getSourceDN()));

        if (demoform.getCustomMessage() == null || demoform.getCustomMessage().isEmpty()) {
            demoform.setCustomMessage(String.format(CANNED_MESSAGE, demoform.getSourceDN()));
        } else {
            demoform.setCustomMessage(String.format(CUSTOM_MESSAGE, demoform.getSourceDN(), demoform.getCustomMessage()));
        }

        model.addAttribute("demoform", demoform);

        if (!bwClient.submitSmsMessage(demoform.getCustomMessage())) {
            LOG.error("Unable to submit to bwClient, message: {}", demoform.getCustomMessage());
            return "resultBadApi";
        }

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

        if (!bwClient.submitCall(demoform.getSourceDN())) {
            LOG.error("Unable to submit to bwClient, call: {}", demoform.getSourceDN());
            return "resultBadApi";
        }

        return "resultCalling";
    }

    private static boolean validDN(final String dn) {
        if (dn == null || dn.isEmpty()) {
            LOG.debug("DN was null or empty");
            return false;
        }

        try {
            PhoneNumber phoneNumber = PHONE_UTIL.parse(new StringBuffer(dn), REGION_CODE);
            return PHONE_UTIL.isValidNumber(phoneNumber);
        } catch (Exception e){
            LOG.error("Got exception parsing DN={}", dn, e);
            return false;
        }

    }

    private static String normalizeDN(final String dn) {
        try {
            PhoneNumber phoneNumber = PHONE_UTIL.parse(new StringBuffer(dn), REGION_CODE);
            return PHONE_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception e) {
            LOG.error("Got exception normalizing DN={}", dn, e);
            return dn;
        }
    }

}
