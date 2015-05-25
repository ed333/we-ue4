package controllers;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;

import models.JeopardyUser;
import models.Player;
import highscore.*;


public class HighScoreController {

    private PublishHighScoreService highscoreService;

    public HighScoreController() {
 
        highscoreService = new PublishHighScoreService();
    }

    public String postHighscore(models.JeopardyGame currentGame) {
        ObjectFactory factory = new ObjectFactory();
        HighScoreRequestType request = factory.createHighScoreRequestType();

        try {
            request.setUserKey("3ke93-gue34-dkeu9");
            GregorianCalendar cal = new GregorianCalendar();

            UserType loserUDT= factory.createUserType();
            JeopardyUser loser = currentGame.getLoser().getUser();
            loserUDT.setFirstName(loser.getFirstName());
            loserUDT.setLastName(loser.getLastName());
            loserUDT.setPassword("");
            loserUDT.setPoints(currentGame.getLoser().getProfit());
            
            cal.setTime(loser.getBirthDate());
            loserUDT.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            loserUDT.setGender(GenderType.fromValue(loser.getGender().toString()));
 
            UserType winnerUDT= factory.createUserType();
            JeopardyUser winner = currentGame.getWinner().getUser();
            
            winnerUDT.setFirstName(winner.getFirstName());
            winnerUDT.setLastName(winner.getLastName());
            winnerUDT.setPassword("");
            winnerUDT.setPoints(currentGame.getWinner().getProfit());
            
            cal.setTime(winner.getBirthDate());
            winnerUDT.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            winnerUDT.setGender(GenderType.fromValue(winner.getGender().toString()));                        
            
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }


        String response = "";

        System.out.println("HIGHSCORE POST SUCCESS NUMBER ONE!");

        try {
            response = highscoreService.getPublishHighScorePort().publishHighScore(request);
            System.out.println("HIGHSCORE POST SUCCESS!");
        } catch (Failure e) {
            System.out.println(e);
            e.printStackTrace();
            System.err.println(e.getFaultInfo().getDetail());
        }
        return response;
    }
}