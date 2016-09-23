package org.kmnet.com.fw.web.logging;

import java.util.HashSet;



public final class LogEncryptionConst {
	
	/** マスキング　**/
	public static final String ENCRYPT_MA = "#";
	
	
	public static HashSet<String> getEncrypt () {
		HashSet<String> encryptSet = new HashSet<String>();

		//【Impacts-A04】暗号化用パラメーターキー
		encryptSet.add("nameKanji");
		encryptSet.add("NameKanji");
		encryptSet.add("NameKana");
		encryptSet.add("Address");
		encryptSet.add("EmailAddress");
		encryptSet.add("UserName");
		encryptSet.add("payment_mail");
		encryptSet.add("CompanyName");
		encryptSet.add("Kana_CompanyName");
		encryptSet.add("DepartmentName");
		encryptSet.add("FaxNo");


		//【Impacts-A06】暗号化用パラメーターキー
		encryptSet.add("systemPassword");
		
		//【Impacts-K】暗号化用パラメーターキー
		encryptSet.add("systemPassword");
		encryptSet.add("storeCardId");
		
		//【Impacts-R01】暗号化用パラメーターキー
		encryptSet.add("wwanReqEmail");
		encryptSet.add("email");
		encryptSet.add("city");
		encryptSet.add("street1");
		encryptSet.add("street2");
		encryptSet.add("custNm");
		encryptSet.add("custNm2");
		encryptSet.add("custNmKana");
		encryptSet.add("custNmKana2");
		encryptSet.add("tel1");
		encryptSet.add("tel2");
		encryptSet.add("tel3");
		encryptSet.add("dtTel1");
		encryptSet.add("dtTel2");
		encryptSet.add("dtTel3");
		encryptSet.add("deptName");
		encryptSet.add("fax1");
		encryptSet.add("fax2");
		encryptSet.add("fax3");

		//【Impacts-R02】暗号化用パラメーターキー
		encryptSet.add("emailAddress");
		encryptSet.add("firstName");
		encryptSet.add("lastName");
		encryptSet.add("firstNameKana");
		encryptSet.add("lastNameKana");
		encryptSet.add("phoneNumber");
		encryptSet.add("phoneNumberDaytime");
		encryptSet.add("faxNumber");
		encryptSet.add("street1");
		encryptSet.add("street2");
		encryptSet.add("city");
		encryptSet.add("department");

		//【Impacts-R受付】暗号化用パラメーターキー
		encryptSet.add("systemPassword");
		encryptSet.add("cardNo");
		encryptSet.add("cardOwnName");
		encryptSet.add("cardSecCd");
		encryptSet.add("cardOwnBirthM");
		encryptSet.add("cardOwnBirthD");
		encryptSet.add("familyNameOrderInfo");
		encryptSet.add("firstNameOrderInfo");
		encryptSet.add("firstNameKanaOrderInfo");
		encryptSet.add("cityOrderInfo");
		encryptSet.add("street1");
		encryptSet.add("phoneNoShigaiOrderInfo");
		encryptSet.add("phoneNoShinaiOrderInfo");
		encryptSet.add("phoneNoCustomerOrderInfo");
		encryptSet.add("dtPhoneNoShigai");
		encryptSet.add("dtPhoneNoShinai");
		encryptSet.add("dtPhoneNoCustomer");
		encryptSet.add("eMailAddress");
		encryptSet.add("confirEeMailAddress");
		encryptSet.add("familyNameDestination");
		encryptSet.add("firstNameDestination");
		encryptSet.add("firstNameKanaDestination");
		encryptSet.add("cityDestination");
		encryptSet.add("street");
		encryptSet.add("phoneNoShigaiDestination");
		encryptSet.add("phoneNoShinaiDestination");
		encryptSet.add("phoneNoCustomerDestination");
		
		//【FOCUS-R】暗号化用パラメーターキー
		encryptSet.add("_cardRegFlg");
		encryptSet.add("payNumRdo2");
		encryptSet.add("familyNameOrderInfo");
		encryptSet.add("firstNameOrderInfo");
		encryptSet.add("familyNameKanaOrderInfo");
		encryptSet.add("firstNameKanaOrderInfo");
		encryptSet.add("zipCodeKamiOrderInfo");
		encryptSet.add("zipCodeShimoOrderInfo");
		encryptSet.add("prefuctureID");
		encryptSet.add("cityOrderInfo");
		encryptSet.add("street1");
		encryptSet.add("phoneNoShigaiOrderInfo");
		encryptSet.add("phoneNoShinaiOrderInfo");
		encryptSet.add("phoneNoCustomerOrderInfo");
		encryptSet.add("dtPhoneNoShigai");
		encryptSet.add("dtPhoneNoShinai");
		encryptSet.add("dtPhoneNoCustomer");
		encryptSet.add("emailAddress");
		encryptSet.add("confirmEmailAddress");
		encryptSet.add("companyNameOrderInfo");
		encryptSet.add("companyNameKanaOrderInfo");
		encryptSet.add("departmentNameOrderInfo");
		encryptSet.add("faxNoShigai");
		encryptSet.add("faxNoShinai");
		encryptSet.add("faxNoCustomer");
		encryptSet.add("deliverInfoDispFlg");
		encryptSet.add("regiAddrRdo");
		encryptSet.add("familyNameDestination");
		encryptSet.add("firstNameDestination");
		encryptSet.add("familyNameKanaDestination");
		encryptSet.add("firstNameKanaDestination");
		encryptSet.add("zipCodeKamiDestination");
		encryptSet.add("zipCodeShimoDestination");
		encryptSet.add("prefuctureIDDestination");
		encryptSet.add("cityDestination");
		encryptSet.add("street");
		encryptSet.add("phoneNoShigaiDestination");
		encryptSet.add("phoneNoShinaiDestination");
		encryptSet.add("phoneNoCustomerDestination");
		encryptSet.add("companyNameDestination");
		encryptSet.add("companyNameKanaDestination");
		encryptSet.add("departmentNameDestination");
		return encryptSet;
	}
	
	public static HashSet<String> getEncryptMaMap () {
		HashSet<String> encryptSet = new HashSet<String>();

		//【Impacts-R01】暗号化用パラメーターキー
		encryptSet.add("systemPassword");
		encryptSet.add("creditInfo");
		encryptSet.add("expiredY");
		encryptSet.add("expiredM");
		encryptSet.add("esCheckPw");
		encryptSet.add("esProcessPw");
		encryptSet.add("ordEsProcessPw");
				
		//【Impacts-R02】暗号化用パラメーターキー
		encryptSet.add("expiredY");
		encryptSet.add("expiredM");
		encryptSet.add("esProcessPw");
		encryptSet.add("carrierProcessPass");
		encryptSet.add("creditInfo");
		encryptSet.add("systemPassword");
		
		//【FOCUS-R】暗号化用パラメーターキー
		encryptSet.add("cardSecCd");
		encryptSet.add("cardNo");
		encryptSet.add("cardValidTermM");
		encryptSet.add("cardValidTermY");
		encryptSet.add("cardSecCd2");
		encryptSet.add("cardOwnName");
		encryptSet.add("cardOwnBirthM");
		encryptSet.add("cardOwnBirthD");
		
		//【Impacts-A04】暗号化用パラメーターキー
		encryptSet.add("cardNo");
		encryptSet.add("CardNo");
		encryptSet.add("secCd");
		encryptSet.add("SecCd");
		encryptSet.add("cardExp");
		encryptSet.add("CardExp");
		encryptSet.add("systemPassword");
		encryptSet.add("SystemPassword");
		encryptSet.add("KanaSei");
		encryptSet.add("KanaMei");
		encryptSet.add("BirthDay");
		encryptSet.add("CAVV3D");
		encryptSet.add("MessageVersionNo3D");
		encryptSet.add("TransactionId3D");
		encryptSet.add("EncodeXId3D");
		encryptSet.add("TransactionStatus3D");
		encryptSet.add("cardOwner");
		encryptSet.add("CardOwner");
		encryptSet.add("birthYear");
		encryptSet.add("birthMonth");
		encryptSet.add("birthDate");
		encryptSet.add("periodMonth");
		encryptSet.add("periodYear");
		encryptSet.add("ProcessPass");
		encryptSet.add("processPass");
		encryptSet.add("KaiinPass");
		encryptSet.add("eLIO");
		encryptSet.add("CAVVAlgorithm3D");
		encryptSet.add("ECI3D");
		encryptSet.add("PANCardNo3D");
		encryptSet.add("cardIdentifyCh");
		encryptSet.add("MerchantPass");
		encryptSet.add("TelNo");
		encryptSet.add("telNo");
		encryptSet.add("birthDay");
		encryptSet.add("encodeXId3D");
		encryptSet.add("messageVersionNo3D");
		encryptSet.add("pANCardNo3D");
		encryptSet.add("transactionId3D");
		encryptSet.add("transactionStatus3D");
		encryptSet.add("BirthYear");
		encryptSet.add("BirthMonth");
		encryptSet.add("BirthDate");
		encryptSet.add("PeriodMonth");
		encryptSet.add("PeriodYear");
		
		//【Impacts-A06】暗号化用パラメーターキー
	    encryptSet.add("ID");
		return encryptSet;
	}
	
	public static boolean checkEncrypt (String key) {
		HashSet<String> encryptSet = getEncrypt();
		if(encryptSet.contains(key)){
			return true;
		}
		return false;
	}
	
	public static boolean checkEncryptMa (String key) {
		HashSet<String> encryptSet = getEncryptMaMap();
		if(encryptSet.contains(key)){
			return true;
		}
		return false;
	}
	
	public static String getMa(int length){
		StringBuilder maBuffer = new StringBuilder();
		for(int i=0;i<length;i++){
			maBuffer.append(ENCRYPT_MA);
		}
		return maBuffer.toString();
	}
	
}