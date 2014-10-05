package com.connectcard.utility;
 
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;

import com.connectcard.dao.impl.MatchupDAOImpl;
import com.connectcard.domain.Matchup;
import com.connectcard.service.RetrieveCitiesAndStates;
import com.connectcard.service.impl.SaveWeeklyMatchupsImpl;
 
public class ServerProxy {
 
	private final String USER_AGENT = "Mozilla/5.0";
	private static final Integer COLUMNS_LENGTH = 17;
    
  
 
	// HTTP GET request
	public BufferedReader sendGet(String url) throws Exception {
		
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
	
		ArrayList<Matchup> matchups = createMatchupObjs(response);
		
		SaveWeeklyMatchupsImpl saveWeeklyMatcupsImpl = new SaveWeeklyMatchupsImpl();
		saveWeeklyMatcupsImpl.saveAllWeeklyMatcups(matchups);

		System.out.println(response.toString());
		return in;
	}
 
	// HTTP POST request
	public void sendPost(String url) throws Exception {
 
		// url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();http://localhost:8080/ConnectCard/videos.htm
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			
			
		}
		in.close();
		
		ArrayList<Matchup> matchups = createMatchupObjs(response);
		
		
		
		//print result
		System.out.println(response.toString());
 
	}

	private ArrayList<Matchup> createMatchupObjs(StringBuffer response) {
		ArrayList <Matchup> matchups = new ArrayList<Matchup>();
		
	
	int homeTeamIndex = response.indexOf("\"columns\" : [") + COLUMNS_LENGTH;
	int endHomeTeamIndex = response.indexOf("]" , homeTeamIndex);
	
	String homeTeamString = response.substring(homeTeamIndex, endHomeTeamIndex);
	 
   ArrayList<String> homeTeamArr = singleQuotesToArray(homeTeamString);
	

	
	
	int awayTeamIndex = response.indexOf("[", endHomeTeamIndex);
	int endAwayTeamIndex = response.indexOf("]", awayTeamIndex);
	
	String awayTeamString = response.substring(awayTeamIndex,endAwayTeamIndex);
	  ArrayList<String> awayTeamArr = singleQuotesToArray(awayTeamString);
	
	int lineIndex = response.indexOf("[", endAwayTeamIndex);
	int endlineIndex = response.indexOf("]", lineIndex);
	
	
	String lineString = response.substring(lineIndex,endlineIndex);
	ArrayList<Float> lineArr = intStringToArray(lineString);
	
	int homeScoreIndex = response.indexOf("[", endlineIndex);
	int endHomeScoreIndex = response.indexOf("]", homeScoreIndex);
	
	String homeScoreString = response.substring(homeScoreIndex,endHomeScoreIndex);
	
	ArrayList<Float> homeScoreArr = intStringToArray(homeScoreString);

	
	int awayScoreIndex = response.indexOf("[", endHomeScoreIndex);
	int endAwayScoreIndex = response.indexOf("]", awayScoreIndex);
	
	String awayScoreString = response.substring(awayScoreIndex,endAwayScoreIndex);
	
	ArrayList<Float> awayScoreArr = intStringToArray(awayScoreString);
	
	if( homeTeamArr.size() > 0 && awayTeamArr.size() > 0 && lineArr.size() > 0){
		
		for (int i = 0; i < homeTeamArr.size(); i++) {
			Matchup matchup = createMatchup(homeTeamArr.get(i), awayTeamArr.get(i), lineArr.get(i), homeScoreArr.get(i),awayScoreArr.get(i)); 
			
			matchups.add(matchup);
		
		}
				
		
	}

	
		
		return matchups;
	}

	private Matchup createMatchup(String homeTeam,
			String awayTeam, Float line,
			Float homeScore, Float awayScore) { 
		
		Matchup matchup = new Matchup();
		
		matchup.setAwayTeam(awayTeam);
		matchup.setHomeTeam(homeTeam);
		matchup.setLine(line);
	if(homeScore > 0){
			matchup.setHomeScore(homeScore);
		}
		
	if(awayScore > 0){
		matchup.setAwayScore(awayScore);
	}
	
		return matchup;
	}

	private ArrayList<String> singleQuotesToArray(String arrayString) {
		StringBuilder tempString = new StringBuilder(arrayString);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		while (tempString.length() > 1){
			
			int first = tempString.indexOf("\'") + 1;
			int second = tempString.indexOf("\'", first + 1);
			
		String token = tempString.substring(first, second );
						arrayList.add(token);
						if(tempString.indexOf(",") != -1){
						tempString = tempString.delete(0, tempString.indexOf(",") + 1);
						}else{
							return arrayList;
						}
			
		}
		
		
		return arrayList;
	}
	
	private ArrayList<Float> intStringToArray(String arrayString) {
		StringBuilder tempString = new StringBuilder(arrayString);
		tempString.delete(0, 1);
		
		ArrayList<Float> arrayList = new ArrayList<Float>();
		//cant find a null
		if(tempString.indexOf("null") == -1){
		
		while (tempString.length() > 1){
			
			int end = tempString.indexOf(",");
		
		if(tempString.indexOf(",") != -1){
			
			String token = tempString.substring(0, end );
		//	if("null".equals(token)))
				Float lineFloat = Float.parseFloat(token);
				arrayList.add(lineFloat);
				
			
						tempString = tempString.delete(0, tempString.indexOf(",") + 1);
						}else{
							String token = tempString.toString();
							Float lineFloat = Float.parseFloat(token);
							arrayList.add(lineFloat);
							return arrayList;
						}
		
			}

		}
		
		
		return arrayList;
	}
 
}