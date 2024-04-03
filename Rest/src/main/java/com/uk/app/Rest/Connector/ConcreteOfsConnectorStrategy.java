///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.uk.app.Rest.Connector;
//
//import com.uk.app.Rest.Connector.OfsRawResponse;
//import com.temenos.tafj.services.OFSService;
//import com.temenos.tafj.services.OFSServicePortType;
//import com.temenos.tafj.services.xsd.ServiceResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// *
// * @author daviestobialex
// */
//@Slf4j
//@Component
//public class ConcreteOfsConnectorStrategy implements OfsConnectorStrategy {
//
//    @Override
//    public OfsRawResponse connect(String request) {
//
//        log.info("OFS REQUEST :: {}", request);
//
//        OfsRawResponse resp = null;
//
//        OFSService service = new OFSService();
//        OFSServicePortType ofs = service.getOFSServiceHttpSoap11Endpoint();
//
//        ServiceResponse response = ofs.invoke(request);
//
//        if (response.getResponses() != null && !response.getResponses().isEmpty()) {
//            String body = response.getResponses().get(0);
//            resp = new OfsRawResponse(response.getReturnCode(), body);
//These
//            log.info("OFS RESPONSE ::  return code -> {} :: body -> {}", resp.getReturnCode(), body);
//        }
//
//        return resp;
//    }
//
//    @Override
//    public OfsRawResponse connect(String request, String wsdl) {
//
//        log.info("OFS REQUEST :: {}", request);
//
//        OfsRawResponse resp = null;
//
//        OFSService service;
//        try {
//            service = new OFSService(new URL(wsdl));
//
//            OFSServicePortType ofs = service.getOFSServiceHttpSoap11Endpoint();
//
//            ServiceResponse response = ofs.invoke(request);
//
//            if (response.getResponses() != null && !response.getResponses().isEmpty()) {
//                String body = response.getResponses().get(0);
//                resp = new OfsRawResponse(response.getReturnCode(), body);
//
//                log.info("OFS RESPONSE ::  return code -> {} :: body -> {}", resp.getReturnCode(), body);
//            }
//        } catch (MalformedURLException ex) {
//            log.info("", ex);
//        }
//
//        return resp;
//    }
//}
