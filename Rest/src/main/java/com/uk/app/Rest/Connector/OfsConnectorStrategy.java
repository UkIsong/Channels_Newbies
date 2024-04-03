/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uk.app.Rest.Connector;

import com.uk.app.Rest.Connector.OfsRawResponse;

/**
 *
 * @author daviestobialex
 */
public interface OfsConnectorStrategy {

    /**
     * Provides a way to update different ways in which the connect
     * implementation may change
     * <p>
     * Integer is the return code, \n String is the response body
     *
     * @param request
     * @return
     */
    OfsRawResponse connect(String request);

    /**
     * Provides a way to update different ways in which the connect
     * implementation may change
     * <p>
     * Integer is the return code, \n String is the response body
     *
     * @param request
     * @param wsdl
     * @return
     */
    OfsRawResponse connect(String request, String wsdl);
}
