package TestScript;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import Pages.DataProvider1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/**
 * @author Zuber
 * @description Test suite of petstore test cases
 * @version 1.0
 **/

public class PetStoreTestCases {
	
	@DataProvider(name = "petStatus")
	public static Object[][] dataProviderMethod() {
		return new Object[][] { { "available" }, { "pending" }, { "sold" } };
	}

	@Test(dataProvider = "petStatus")
	public void TC01_getPetDetailsByStatus(String status) throws IOException,
			ParseException, NullPointerException {
		URL url = new URL(
				"https://petstore.swagger.io/v2/pet/findByStatus?status="
						+ status);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP Error code : "
					+ conn.getResponseCode());
		}
		Scanner sc = new Scanner(url.openStream());
		String inline = "";
		while (sc.hasNext()) {
			inline += sc.nextLine();
		}
		inline = "{\n" + "\"results\":" + inline + "\n}";
		System.out.println("\nJSON data in string format");
		System.out.println("{\n" + "\"results\":" + inline + "\n}");
		sc.close();
		conn.disconnect();

		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(inline);
		JSONArray jsonarr_completeJson = (JSONArray) jobj.get("results");
		System.out.println("PET DETAILS : \n");
		Reporter.log("PET DETAILS : \n");

		for (int i = 0; i < jsonarr_completeJson.size(); i++) {
			JSONObject jsonobj_CompleteJson = (JSONObject) jsonarr_completeJson.get(i);
			JSONArray jsonarr_Tag = (JSONArray) jsonobj_CompleteJson.get("tags");
			if (jsonarr_Tag.toString().trim() != "") {
				for (int j = 0; j < jsonarr_Tag.size(); j++) {
					JSONObject jsonobj_Tag = (JSONObject) jsonarr_Tag.get(j);
					System.out.println("Tag : " + jsonobj_Tag.get("name"));
					Reporter.log("Tag : " + jsonobj_Tag.get("name"));
				}
			}
			System.out.println("NAME: " + jsonobj_CompleteJson.get("name"));
			Reporter.log("NAME : " + jsonobj_CompleteJson.get("name"));
			System.out.println("Status: "
					+ jsonobj_CompleteJson.get("status").toString().toUpperCase() + "\n");
			Reporter.log("Status: "
					+ jsonobj_CompleteJson.get("status").toString().toUpperCase() + "\n");
			Reporter.log("-------------------------");
		}
	}


	@DataProvider(name = "petIDDetails")
	public Object[][] GetPetDetailsFormDetaTable() {
		DataProvider1 DP = new DataProvider1();
		Object[][] PetDet = DP.DataFromDataTable(System.getProperty("user.dir")
				+ "\\src\\DataTable\\PetDetails_ID.xls");
		return PetDet;
	}

	@Test(dataProvider = "petIDDetails")
	public void TC02_SearchPetByID(String ID) throws Exception {
		try{
		System.out.println(ID);
		URL url = new URL("https://petstore.swagger.io/v2/pet/" + ID);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		Assert.assertEquals(conn.getResponseCode(), 200);
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : Pet with ID : "
					+ ID.toString() + " is not found in the store"
					);
		} else {
			Reporter.log("Pet with ID - " + ID.toString()
					+ " is found in store as expected");
		}
		}
		catch(Exception e){
			Reporter.log("Failed : Pet with ID : "
					+ ID.toString() + " is not found in the store");
					
			e.printStackTrace();
		}

	}

	@DataProvider(name = "AddUpdatePet")
	public Object[][] GetPetDetailstoAddIntoStore(){
		DataProvider1 DP = new DataProvider1();
		Object[][] PetDet = DP.DataFromDataTable(System.getProperty("user.dir")
				+ "\\src\\DataTable\\AddUpdatePets.xls");
		return PetDet;
	}
	
	@Test(dataProvider = "AddUpdatePet")
	public void TC03_AddUpdatePet(String ID,String Name, 
			String PhotoURL, String Status) throws IOException {
		try{
		final String POST_PARAMS = 		
		"{ \"id\": " +ID + ", \"category\": { \"id\": " + ID + ", \"name\": \"" + Name + "\" }, " +
				"\"name\": \"" + Name + "\", \"photoUrls\": [ \"" + PhotoURL + "\" ], " +
				"\"/tags\": [ { \"id\": " + ID + ", \"/name\": \"" + Name + "\" } ], \"status\": \"" +Status+ "\"}";
		System.out.println(POST_PARAMS);
		URL obj = new URL("https://petstore.swagger.io/v2/pet");
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream outpuststream = postConnection.getOutputStream();
		outpuststream.write(POST_PARAMS.getBytes());
		outpuststream.flush();
		outpuststream.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : "
				+ postConnection.getResponseMessage());
		if(responseCode == 200){
			Reporter.log("Pet with Name - " + Name + " and ID - " + ID + " is added successfully into the store");
			
		}
		}
		catch(Exception e)
		{
			Reporter.log("Pet with Name - " + Name + " and ID - " + ID + " is not added. Please check the error log");
			e.printStackTrace();
		}
	}

	
@DataProvider(name = "PetIDForDeletion")
public Object[][] GetPetDetailstoDeleteFromStore(){
		DataProvider1 DP = new DataProvider1();
		Object[][] PetDet = DP.DataFromDataTable(System.getProperty("user.dir")
				+ "\\src\\DataTable\\DeletePets.xls");
		return PetDet;
	}
		


@Test(dataProvider = "PetIDForDeletion")
public void TC04_DeletePet(String PetID){
	URL url;
	try {
		url = new URL("https://petstore.swagger.io/v2/pet/" + PetID);
	
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("DELETE");
	conn.setRequestProperty("Accept", "application/json");
	if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : Pet does not exits. Please use correct Pet ID : "
				+ conn.getResponseCode());
	}
	else{
		Reporter.log("Pet with ID - " + PetID + " has been deleted successfully");
	}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	

}
