/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uk.app.Rest.Connector;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author odavies
 */
@Slf4j
public class OfsUtility {


    public static String genericOfsGenerator(
            String enquiry,
            String username,
            String password,
            String filters,
            String companyCode) {
        return "ENQUIRY.SELECT,," + username + "/" + password + "/" + companyCode + "," + enquiry + "," + filters;
    }

    public static List<List<Optional<String>>> genericOfsDataExtraction(String message) {
        ArrayList<List<Optional<String>>> records = new ArrayList<>();
        String[] lines = message.split(",\"");

        String headerline = lines[0];
        List<Optional<String>> headers = new ArrayList<>();

        for (String header : headerline.split("/")) {
            try {
                headers.add(Optional.ofNullable(header.split("::")[1]));
            } catch (Exception d) {
                headers.add(Optional.ofNullable(header.split(":")[1]));
            }
        }

        records.add(headers);

        for (int i = 1; i < lines.length; i++) {
            headers = new ArrayList<>();
            for (String s : lines[i].split("\"\t\"")) {
                headers.add(Optional.ofNullable(s));
            }
            records.add(headers);
        }
        return records;
    }


    public static String hash512(String StringToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(StringToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return generatedPassword;
    }

    public static String notNullTrim(String str) {
        return str == null ? "" : str.trim();
    }

    public static Boolean IsSuccessful(String ofsresposne) {

        try {
            return ((!ofsresposne.isEmpty()) && ofsresposne.split("/")[2].startsWith("1"));
        } catch (Exception s) {
            return false;
        }
    }

    public String generateReversalTransactionString(OfsParam param) {
        StringBuilder output = new StringBuilder();

        output.append(param.getOperation().toUpperCase()).append(',');

        output.append(param.getVersion().toUpperCase());

        String options = String.join("/", param.getOptions());

        output.append(options.toUpperCase()).append(",");

        String credentials = String.join("/", param.getCredentials());

        output.append(credentials).append(",");

        output.append(param.getTransactionId()).append(",");
        String result = output.toString();

        result = result.substring(0, result.length() - 1);

        return result;
    }

    public static String generateOFSTransactString(OfsParam param) {
        StringBuilder output = new StringBuilder();

        output.append(param.getOperation().toUpperCase()).append(',');

        // output.append(param.getVersion());
        output.append(param.getVersion().toUpperCase());

        String options = String.join("/", param.getOptions());

        output.append(options.toUpperCase()).append(",");

        String credentials = String.join("/", param.getCredentials());

        credentials.trim();

        output.append(credentials).append(",");

        if (param.getMessageReference() != null) {
            String messageID = String.join("/", "", param.getMessageReference());
            output.append(messageID);
        }

        output.append(param.getTransactionId()).append(",");

        param.getDataItems().stream().forEach((dataitem) -> {
            Boolean isMultivalue = dataitem.getItemValues().size() > 1;

            int valuecount = 1;

            for (String value : dataitem.getItemValues()) {
                output.append(dataitem.getItemHeader()).append((isMultivalue ? ":" + valuecount + "=" + value : "=" + value)).append(",");

                valuecount = valuecount + 1;
            }
        });

        String result = output.toString();

        result = result.substring(0, result.length() - 1);

        return result;
    }

    public static String generateUpdateRecordString(OfsParam param) {
        StringBuilder output = new StringBuilder();

        output.append(param.getOperation().toUpperCase()).append(',');

        // output.append(param.getVersion());
        output.append(param.getVersion().toUpperCase());

        String options = String.join("/", param.getOptions());

        output.append(options.toUpperCase()).append(",");

        String credentials = String.join("/", param.getCredentials());

        output.append(credentials).append(",");

        output.append(param.getTransactionId()).append(",");

        param.getDataItems().stream().forEach((dataitem) -> {
            Boolean isMultivalue = dataitem.getItemValues().size() > 1;

            int valuecount = 1;

            for (String value : dataitem.getItemValues()) {
                output.append(dataitem.getItemHeader()).append((isMultivalue ? ":" + valuecount + "=" + value : "=" + value)).append(",");

                valuecount = valuecount + 1;
            }
        });

        String result = output.toString();

        result = result.substring(0, result.length() - 1);

        return result;
    }

    public static String extractTransactionReference(String ofsRawResponse) {

        String refid = ofsRawResponse.split("/")[0];
        if (!"".equals(refid) && refid.contains("FT")) {
            return refid;
        }

        throw new RuntimeException("transaction not successful as payment reference could not be extracted");
    }

    public static String escape(String text) {

        return text.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
    }

    public static String escapeSpecialCharacters(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= '\u0000' && c <= '\u001f' || c == '\u007f' || c == '\u0080' || c == '\u009f') {
                // Escape control characters and some special characters in the ASCII range
                output.append(String.format("\\u%04X", (int) c));
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }


}
