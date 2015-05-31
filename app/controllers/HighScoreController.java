package controllers;

import highscore.Failure;
import highscore.GenderType;
import highscore.HighScoreRequestType;
import highscore.ObjectFactory;
import highscore.PublishHighScoreService;
import highscore.UserDataType;
import highscore.UserType;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import models.JeopardyUser;
import play.Logger;


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

            UserType loserUT= factory.createUserType();
            JeopardyUser loser = currentGame.getLoser().getUser();
            loserUT.setFirstName(loser.getFirstName());
            loserUT.setLastName(loser.getLastName());
            loserUT.setPassword("");
            loserUT.setPoints(currentGame.getLoser().getProfit());
            
            cal.setTime(loser.getBirthDate());
            //cal = new GregorianCalendar(1990,1,1);
            loserUT.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            loserUT.setGender(GenderType.fromValue(loser.getGender().toString()));
 
            UserType winnerUT= factory.createUserType();
            JeopardyUser winner = currentGame.getWinner().getUser();
            
            winnerUT.setFirstName(winner.getFirstName());
            winnerUT.setLastName(winner.getLastName());
            winnerUT.setPassword("");
            winnerUT.setPoints(currentGame.getWinner().getProfit());
            
            cal.setTime(winner.getBirthDate());
            //cal= new GregorianCalendar(1980,5,5);
            winnerUT.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
            winnerUT.setGender(GenderType.fromValue(winner.getGender().toString())); 
            
            UserDataType udt = factory.createUserDataType();
            udt.setLoser(loserUT);
            udt.setWinner(winnerUT);
            request.setUserData(udt);
            
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }


        String response = "";

        try {
            response = highscoreService.getPublishHighScorePort().publishHighScore(request);
            Logger.debug("Response received: "+ response + ".");

        } catch (Failure e) {
            //System.out.println(e);
            //e.printStackTrace();
        	Logger.error(e.getMessage());
            Logger.error(e.getFaultInfo().getDetail());
        }
        return response;
    }
}