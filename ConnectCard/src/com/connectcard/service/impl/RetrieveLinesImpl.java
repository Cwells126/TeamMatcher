package com.connectcard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.connectcard.dao.MatchupDAO;
import com.connectcard.dao.impl.CityDAOImpl;
import com.connectcard.dao.impl.StateDAOImpl;
import com.connectcard.domain.City;
import com.connectcard.domain.Matchup;
import com.connectcard.domain.State;
import com.connectcard.service.RetrieveLines;

@Component
public class RetrieveLinesImpl implements RetrieveLines{
    @Autowired
    private MatchupDAO matchupDAO;
    
    
    @Override
        public List<Matchup> retrieveAllLines() {
        return matchupDAO.getMatchups();
        
    }
    
    
    @Override
    public List<Matchup> retrieveLinesByWeek(short week) {
        return matchupDAO.getMatchupsByGameId(week); 
    }
    
    
    /**
     * This method converts a list of objects into a delimited string
     * @param list the list to convert to a delimited string
     * @param delimiter the delimiter to use
     * @return the list as a delimited string
     */
    private static String convertListToDelimitedStringWithQoutes(List list, String delimiter) {
        StringBuilder sb = new StringBuilder();

        for (Object obj : list) {
            sb.append("\"").append(String.valueOf(obj)).append("\"").append(',');
        }

        sb.deleteCharAt(sb.length() - 1); //delete last comma

        return sb.toString();
    }

  
}
