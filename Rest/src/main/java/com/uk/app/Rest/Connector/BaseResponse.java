//package com.uk.app.Rest.Connector;
//
//public BaseResponse t24TransferDebit(NIPFundsTransferEntity record) {
//    BaseResponse performPostREST = new BaseResponse();
////        NIPResponseCodes _respcodes;
//    com.agency.nip.inward.service.enums.NIPResponseCodes _respcodes;
//    String errMsg ="";
//
//    log.info("insti Code ::: " + record.getDeditAccountInstitutionCode());
//    InstitutionDetails details = getInstitutionDetails(record.getDeditAccountInstitutionCode());
//
//    if (details == null) {
//        _respcodes = com.agency.nip.inward.service.enums.NIPResponseCodes.NIP_16;
//        performPostREST.setResponseCode(_respcodes.getCode());
//        performPostREST.setResponseDescription(_respcodes.getDescription());
//        return performPostREST;
//    }
//
//    OfsParam.OfsParamBuilder builderRef = new OfsParam.OfsParamBuilder("NIBBS.FT.REF.TABLE",
//            "", Arrays.asList(username, password, code)
//    ).setOptions(Arrays.asList("", "I", "VALIDATE", "2", "0"))
//            .setTransactionId(record.getRequestId().replace("/", "").replace("_", ""));
//
////        RestServicesOfsResponse restServicesOfsResponse = processRequestRest(builder, tafjId, tafjPass);
////        String ofsResponse = restServicesOfsResponse.getOfsResponse();
//    String ofsResponseRef = processRequest(builderRef).getRawResponse();
//
//
//    log.info("Ref Table ::: ofsResponseRef {}", ofsResponseRef);
//    if (!(ofsResponseRef.indexOf("T24.ID") > 0)) {
//        List<OfsParam.DataItem> builderNar = getDataItemList(escape(record.getRemarks().trim()),34);
//        log.info("prepare Ofs");
//        OfsParam.OfsParamBuilder builder = new OfsParam.OfsParamBuilder("FUNDS.TRANSFER",
//                "FT.REQ.DIRECT.DEBIT.NIP", Arrays.asList(username, password, code)
//        ).setOptions(Arrays.asList("", "I", "PROCESS", "2", "0"))
//                //                .addDataItem(new OfsParam.DataItem("DEBIT.CURRENCY", Arrays.asList("NGN")))
//                .addDataItem(new OfsParam.DataItem("CREDIT.CURRENCY", Arrays.asList("NGN")))
//                .addDataItem(new OfsParam.DataItem("DEBIT.CURRENCY", Arrays.asList("NGN")))
//                .addDataItem(new OfsParam.DataItem("DEBIT.AMOUNT", Arrays.asList(record.getAmount().toString())))
//                .addDataItem(new OfsParam.DataItem("CHARGE.AMT", Arrays.asList("NGN" + record.getTransactionFee().toString())))
//                .addDataItem(new OfsParam.DataItem("DEBIT.ACCT.NO", Arrays.asList(record.getDebitAccountNumber().trim())))
//                //                                    .addDataItem(new OfsParam.DataItem("CREDIT.ACCT.NO", Arrays.asList(details.getPayableAccount().trim())))
//                .addDataItem(new OfsParam.DataItem("Dest.Inst.Code", Arrays.asList("090194")))
//                .addDataItem(new OfsParam.DataItem("CREDIT.ACCT.NO", Arrays.asList(details.getPayableAccount().trim())))
//                .addDataItem(new OfsParam.DataItem("SessionID", Arrays.asList(record.getRequestId().trim())))
////                    .addDataItem(new OfsParam.DataItem("Orig.Acct.Name", Arrays.asList(escape(record.getOriginatorAccountName().trim()))))
//                .addDataItem(new OfsParam.DataItem("REM.REF", Arrays.asList(escape(escape(record.getRemarks()).length() > 25 ?
//                        escape(record.getRemarks()).trim().substring(0, 25) : escape(record.getRemarks()).trim()))))
////                    .addDataItem(new OfsParam.DataItem("PAYMENT.DETAILS", Arrays.asList(escape(escape(record.getRemarks()).length() > 25 ?
////                                    escape(record.getRemarks()).trim().substring(0, 25) : escape(record.getRemarks()).trim()))))
//                .addDataItemList(builderNar)
//                .setTransactionId("");
////TODO:ESCAPE SPECIAL CHAR "Transaction from me &amp;amp; you"
//
////        RestServicesOfsResponse restServicesOfsResponse = OfsUtility.processRequestRest(builder, tafjId, tafjPass);
////        String ofsResponse = restServicesOfsResponse.getOfsResponse();
//        String ofsResponse = processRequest(builder).getRawResponse();
//
//        log.info("DirectDebit Response " + ofsResponse);
//
//        if (IsSuccessful(ofsResponse)) {
//            _respcodes = com.agency.nip.inward.service.enums.NIPResponseCodes.NIP_00;
//            //                                performPostREST.setTransactionRef();
//
//            String t24ref = ofsResponse.split("/")[0];
//            log.info("TransactionRef ::: {}", t24ref);
//
//            OfsParam.OfsParamBuilder builder2 = new OfsParam.OfsParamBuilder("NIBBS.FT.REF.TABLE",
//                    "", Arrays.asList(username, password, code)
//            ).setOptions(Arrays.asList("", "I", "PROCESS", "2", "0"))
//                    .addDataItem(new OfsParam.DataItem("T24.ID", Arrays.asList(t24ref)))
//                    .setTransactionId(record.getRequestId().replace("/", "").replace("_", ""));
//
//            log.info(" OFS Request {}", builder2);
////                RestServicesOfsResponse restServicesOfsResponse2 = processRequestRest(builder2, tafjId, tafjPass);
////                String ofsResponse2 = restServicesOfsResponse2.getOfsResponse();
//            String ofsResponse2 = processRequest(builder2).getRawResponse();
//            log.info(" OFS Result  " + ofsResponse2);
//
//        } else {
//            String[] a_msg = ofsResponse.split(",");
//            log.info("a_msg >>>" + Arrays.toString(a_msg));
//            String msg = a_msg[1];
//            log.info("msg >> {}", msg);
////                if (a_msg[2].isEmpty())//Todo: manage more thAN ON ERROR FROM T24
//            log.info(getNIBBsCode(msg).getResponseCode().toString());
//            _respcodes = com.agency.nip.inward.service.enums.NIPResponseCodes.getNIPAgencyResponseMapFromInlaksCode(getNIBBsCode(msg).getResponseCode());
//            log.info("_respcodes   >>> {}", _respcodes);
//            log.info("_respcodes code  >>> {}", _respcodes.getCode());
////                _respcodes = NIPResponseCodes.getNIPAgencyResponseMapFromInlaksCode(getNIBBsCode(msg).getResponseCode());
//            errMsg = msg;
//        }
//
//        performPostREST.setResponseCode(_respcodes.getCode());
//        log.info("t24 ResponseCode >>> {}", performPostREST.getResponseCode());
//        performPostREST.setResponseDescription(_respcodes.getDescription() + errMsg);
//        performPostREST.setTransactionRef(ofsResponse.split("/")[0]);
//
//    } else {
//        _respcodes = com.agency.nip.inward.service.enums.NIPResponseCodes.NIP_00;
//        performPostREST.setResponseCode(_respcodes.getCode());
//        performPostREST.setResponseDescription(_respcodes.getDescription());
//    }
//    return performPostREST;
//}