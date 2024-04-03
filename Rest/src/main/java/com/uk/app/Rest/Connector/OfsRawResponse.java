/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uk.app.Rest.Connector;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author daviestobialex
 */
@Data
@AllArgsConstructor
public class OfsRawResponse {

    private Integer returnCode;
    private String rawResponse;
}
