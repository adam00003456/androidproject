package com.example.adam.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Adam on 8/12/2015.
 */
public class voicecontroller extends controller implements RecognitionListener {

    /** Sensor that detects gestures. Calls the appropriate
     *  functions when the motions are recognized. */
    private AudioManager mAudioManager;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOGTAG = "speechlistener";
    public boolean isTapped = false;
    public boolean flipper = false;



    public voicecontroller(Activity activity, Context context){
        mAudioManager = (AudioManager) activity.getSystemService(context.AUDIO_SERVICE);
        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                activity.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        mAudioManager.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);


    }







    public void display(Canvas canvas) {

        Rect rectangle = new Rect();
        rectangle.set(0, (int)(canvas.getHeight() * .80), canvas.getWidth(), canvas.getHeight());
        super.maxwidthforship = canvas.getWidth();
        Paint grey = new Paint();
        grey.setColor(Color.GRAY);
        grey.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectangle, grey);
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setTextSize(100);
        canvas.drawText("Voice Mode", (canvas.getWidth() / 4), (int)(canvas.getHeight() * .90), white);
    }


    public void callisDoubleTapped(){

        if(isTapped == true){
            speech.startListening(recognizerIntent);

        }else{
            speech.stopListening();

        }
    }




    @Override
    public void handleMovement(Spaceship spaceship) {

        if (touchedleft == true && touchedright == false) {
            int tempx = spaceship.getX();
            tempx -= 1;
            if (tempx >= 0)
                spaceship.setX(tempx);
        }

        if (touchedright == true && touchedleft == false) {
            int tempx;
            tempx = spaceship.getX();
            tempx += 1;
            if (tempx <= maxwidthforship)
                spaceship.setX(tempx);
        }


    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(LOGTAG, "Ready for speech");

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOGTAG, "Beginning of the speech");

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOGTAG, "onRmsChanged: " + rmsdB);

    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        Log.i(LOGTAG, "End of the speech");

    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOGTAG, "End of the speech");
        isTapped = false;
        flipper = true;
        callisDoubleTapped();
    }

    @Override
    public void onError(int error) {
        String errortext = getErrorText(error);
        Log.i(LOGTAG, "I hit error here and the error was " + errortext);

    }


    public String getErrorText(int errorCode) {
        String message;
        boolean busy = false;
        switch (errorCode){
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                busy = true;
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Did not understand, please try again.";
                break;
        }
        if(busy==false){

            isTapped = false;
        }

        return message;
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOGTAG, "Results of the speech");
        ArrayList<String> resultlist =
        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (int i = 0; i < resultlist.size(); i++) {
            Log.i(LOGTAG, resultlist.get(i) + " ");
            if (resultlist.get(i).startsWith("l")){
                touchedleft = true;
                touchedright = false;
                break;
            }else if(resultlist.get(i).startsWith("r")){
                touchedleft = false;
                touchedright = true;
                break;
            }else if(resultlist.get(i).startsWith("f")){
                touchedfire = true;
                break;
            }
        }
        isTapped = false;


    }


    public void onTouch(){
        if(isTapped == false) {
            isTapped = true;
            callisDoubleTapped();
        }
    }

    //unlike other controllers, needs to be in update because a gameplay action is not expected to happen right
    //after an on screen event(like touch in this example)
    public void update(){
        this.handleMovement(spaceship);
        if (this.touchedfire == true) {
            spaceship.shoot(spaceship.getX(), spaceship.getY());
            this.touchedfire = false;
        }
    }



    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(LOGTAG, "Partial results");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOGTAG, "On event");
    }
}
