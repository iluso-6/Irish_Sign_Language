/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signlanguage;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.READY;





/**
 *
 * @author Shay
 */
public class FXMLDocumentController implements Initializable {
int idx = 97;
char c = (char) idx; //should yield 'a';
   
 private Image image;
 private String current;
@FXML MenuButton menu; 
@FXML ImageView back;
@FXML ImageView front;
@FXML Button prev;
@FXML Button next;
@FXML TextField textbox;
@FXML Button play;

    private void showPics(int idx){   
        c = (char) idx;

        current = "img/"+ c +".png";
        image = new Image(current);
        back.setImage(image);
        current = "img/"+c+"s.png";
        image = new Image(current);
        front.setImage(image);
        playSnd("click");
    }
    
    @FXML private void handleButtonAction(ActionEvent event) {
               prev.setDisable(false);
               next.setDisable(false); 
        
        
        if(event.getSource()==next){
          idx++;
        }else if(event.getSource()==prev){
          idx--;
        };
        if(idx<=97){
            idx=97;
            prev.setDisable(true);
        }else if(idx>=122){
            idx=122;
            next.setDisable(true);
        };
           showPics(idx);
    };
    
    @FXML private void handlePlayRequest(ActionEvent event) {
    String test;
    test = (textbox.getText()).toLowerCase();
    textbox.setText("");
   
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        int i;
        String outTxt = "";
        {
            this.i = 0;
        }
        public void run() {
            
            if(i<test.length()){
            char out = test.charAt(i);
            outTxt += out;
            int codeId = (int) test.charAt(i);
            if(codeId>=97 && codeId<=122){
            showPics(codeId);
            textbox.setText("");
            textbox.setText(outTxt);
          
            i++;
            }else{
            outTxt = outTxt + " ";    
            i++;
            }
            }else{
            timer.cancel();
            }
            System.out.println(outTxt);
        }
    }, 0, 800);
    
        System.out.println("play: " + test);
    };  
    
    @FXML private void handleMenuReset(ActionEvent event) {
            playSnd("gloop");
            idx = 97;
            current = "img/a.png";
            image = new Image(current);
            back.setImage(image);
            current = "img/as.png";
            image = new Image(current);
            front.setImage(image);
            
            textbox.setText("");
    }; 
    
    @FXML private void handleMenuExit(ActionEvent event) {
    System.out.println("EXIT");
    System.exit(0);
    }; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     prev.setDisable(true);
    } 
    
    
    Runnable onEndOfMedia;
    private void playMedia(Media m){
    if (m != null){
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
        onEndOfMedia = mp.getOnEndOfMedia();
        
      
    }
}

public void playSnd(String snd){
    try{
        Media someSound = new Media(getClass().getResource("/sounds/"+snd+".mp3").toString());
        playMedia(someSound);
    }catch(Exception error){
         System.out.println("Error: " + error);
    }

}
    
    
    
    
}
