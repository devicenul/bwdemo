package net.merc.bandwidth.demo.bwclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.merc.bandwidth.demo.config.IApiConfig;
import net.merc.bandwidth.demo.config.INumberConfig;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class BandwidthClient {
    private static final Logger LOG = LoggerFactory.getLogger(BandwidthClient.class);

    private static final String BW_URL = "https://api.catapult.inetowrk.com/%s";
    private static final String BW_MSG_PATH = "v1/users/%s/messages";

    private final String apiKey;
    private final String apiUser;
    private final String apiSecret;

    private final String sourceDN;
    private final String destDN;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BandwidthClient(final IApiConfig apiConfig, final INumberConfig numberConfig) {
        Validate.notNull(apiConfig);
        Validate.notNull(numberConfig);

        apiKey = apiConfig.getApiKey();
        apiUser = apiConfig.getApiUser();
        apiSecret = apiConfig.getApiSecret();

        Validate.notEmpty(apiKey);
        Validate.notEmpty(apiUser);
        Validate.notEmpty(apiSecret);

        sourceDN = numberConfig.getSourceDN();
        destDN = numberConfig.getDestDN();

        Validate.notEmpty(sourceDN);
        Validate.notEmpty(destDN);

        LOG.info("Instantiated.");
    }

    public boolean submitSmsMessage(final String message) {
        if (message == null || message.isEmpty()) {
            LOG.debug("Message was empty.");
            return false;
        }

        final SmsData smsData = new SmsData();
        smsData.setFrom(sourceDN);
        smsData.setTo(destDN);
        smsData.setText(message);

        try {
            final String apiData = objectMapper.writeValueAsString(smsData);
            LOG.trace("apiData:\n{}", apiData);

            final String url = String.format(BW_URL, String.format(BW_MSG_PATH, apiKey));
            LOG.trace("url: {}, url");

            final StringEntity entity = new StringEntity(apiData);

            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiUser, apiSecret);
            provider.setCredentials(AuthScope.ANY, credentials);

            HttpClient client = HttpClientBuilder.create()
                    .setDefaultCredentialsProvider(provider)
                    .build();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            httpPost.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("Unable to post to SMS API: {}:{}", response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                return false;
            }

        } catch (Exception e) {
            LOG.error("Caught exception trying to send SMS to API", e);
            return false;
        }

        return true;
    }




}
