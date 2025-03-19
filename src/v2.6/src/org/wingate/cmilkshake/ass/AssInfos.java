/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.ass;

/**
 *
 * @author util2
 */
public class AssInfos {

    public enum AssInfosType{
        pathname, software, weblinks, title, authors, translators,
        editors, timers, checkers, synchpoint, updateby, updates,
        collisions, playresx, playresy, playdepth, timerspeed,
        wrapstyle, audios, video, scripttype, karaokemakers;
    }
    
    private String ScriptPathName = "";
    private String ScriptSoftware = "Make with KaraModeFunsub/Funsub Project";
    private String ScriptWeblinks = "";
    private String ScriptTitle = "";
    private String ScriptAuthors = "";
    private String ScriptTranslators = "";
    private String ScriptEditors = "";
    private String ScriptTimers = "";
    private String ScriptCheckers = "";
    private String ScriptSynchPoint = "";
    private String ScriptUpdateBy = "";
    private String ScriptUpdates = "";
    private String ScriptCollisions = "";
    private String ScriptPlayResX = "640";
    private String ScriptPlayResY = "480";
    private String ScriptPlayDepth = "";
    private String ScriptAudios = "";
    private String ScriptVideo = "";
    private String ScriptTimerSpeed = "100.0000";
    private String ScriptWrapStyle = "";
    private String ScriptType = "";
    private String KaraokeMakers = "";
    
    public AssInfos() {
    }
    
    public void add(String raw){        
        if(raw.startsWith("Title") && !raw.substring("Title:".length()).isEmpty()){
            setElement(AssInfosType.title, raw.substring("Title: ".length()));
        }

        if(raw.startsWith("Original Script") && !raw.substring("Original Script:".length()).isEmpty()){
            setElement(AssInfosType.authors, raw.substring("Original Script: ".length()));
        }

        if(raw.startsWith("Original Translation") && !raw.substring("Original Editing:".length()).isEmpty()){
            setElement(AssInfosType.translators, raw.substring("Original Editing: ".length()));
        }

        if(raw.startsWith("Original Editing") && !raw.substring("Title:".length()).isEmpty()){
            setElement(AssInfosType.editors, raw.substring("Title:".length()));
        }

        if(raw.startsWith("Original Timing") && !raw.substring("Original Timing:".length()).isEmpty()){
            setElement(AssInfosType.timers, raw.substring("Original Timing: ".length()));
        }

        if(raw.startsWith("Original Script Checking") && !raw.substring("Original Script Checking:".length()).isEmpty()){
            setElement(AssInfosType.checkers, raw.substring("Original Script Checking: ".length()));
        }

        if(raw.startsWith("Synch Point") && !raw.substring("Synch Point:".length()).isEmpty()){
            setElement(AssInfosType.synchpoint, raw.substring("Synch Point: ".length()));
        }

        if(raw.startsWith("Script Updated By") && !raw.substring("Script Updated By:".length()).isEmpty()){
            setElement(AssInfosType.updateby, raw.substring("Script Updated By: ".length()));
        }

        if(raw.startsWith("Update Details") && !raw.substring("Update Details:".length()).isEmpty()){
            setElement(AssInfosType.updates, raw.substring("Update Details: ".length()));
        }

        if(raw.startsWith("ScriptType") && !raw.substring("ScriptType:".length()).isEmpty()){
            setElement(AssInfosType.scripttype, raw.substring("ScriptType: ".length()));
        }

        if(raw.startsWith("Collisions") && !raw.substring("Collisions:".length()).isEmpty()){
            setElement(AssInfosType.collisions, raw.substring("Collisions: ".length()));
        }

        if(raw.startsWith("PlayResX") && !raw.substring("PlayResX:".length()).isEmpty()){
            setElement(AssInfosType.playresx, raw.substring("PlayResX: ".length()));
        }

        if(raw.startsWith("PlayResY") && !raw.substring("PlayResY:".length()).isEmpty()){
            setElement(AssInfosType.playresy, raw.substring("PlayResY: ".length()));
        }

        if(raw.startsWith("PlayDepth") && !raw.substring("PlayDepth:".length()).isEmpty()){
            setElement(AssInfosType.playdepth, raw.substring("PlayDepth: ".length()));
        }

        if(raw.startsWith("Timer") && !raw.substring("Timer:".length()).isEmpty()){
            setElement(AssInfosType.timerspeed, raw.substring("Timer: ".length()));
        }

        if(raw.startsWith("Audio") && !raw.substring("Audio:".length()).isEmpty()){
            setElement(AssInfosType.audios, raw.substring("Audio: ".length()));
        }

        if(raw.startsWith("Video") && !raw.substring("Video:".length()).isEmpty()){
            setElement(AssInfosType.video, raw.substring("Video: ".length()));
        }
    }
    
    public void setElement(AssInfosType ait, String value){
        switch(ait){
            case pathname: ScriptPathName = value; break;
            case software: ScriptSoftware = value; break;
            case weblinks: ScriptWeblinks = value; break;
            case title: ScriptTitle = value; break;
            case authors: ScriptAuthors = value; break;
            case translators: ScriptTranslators = value; break;
            case editors: ScriptEditors = value; break;
            case timers: ScriptTimers = value; break;
            case checkers: ScriptCheckers = value; break;
            case synchpoint: ScriptSynchPoint = value; break;
            case updateby: ScriptUpdateBy = value; break;
            case updates: ScriptUpdates = value; break;
            case collisions: ScriptCollisions = value; break;
            case playresx: ScriptPlayResX = value; break;
            case playresy: ScriptPlayResY = value; break;
            case playdepth: ScriptPlayDepth = value; break;
            case timerspeed: ScriptTimerSpeed = value; break;
            case wrapstyle: ScriptWrapStyle = value; break;
            case audios: ScriptAudios = value; break;
            case video: ScriptVideo = value; break;
            case scripttype: ScriptType = value; break;
            case karaokemakers: KaraokeMakers = value; break;
        }
    }
    
    public String getElement(AssInfosType ait){
        switch(ait){
            case pathname: return ScriptPathName;
            case software: return ScriptSoftware;
            case weblinks: return ScriptWeblinks;
            case title: return ScriptTitle;
            case authors: return ScriptAuthors;
            case translators: return ScriptTranslators;
            case editors: return ScriptEditors;
            case timers: return ScriptTimers;
            case checkers: return ScriptCheckers;
            case synchpoint: return ScriptSynchPoint;
            case updateby: return ScriptUpdateBy;
            case updates: return ScriptUpdates;
            case collisions: return ScriptCollisions;
            case playresx: return ScriptPlayResX;
            case playresy: return ScriptPlayResY;
            case playdepth: return ScriptPlayDepth;
            case timerspeed: return ScriptTimerSpeed;
            case wrapstyle: return ScriptWrapStyle;
            case audios: return ScriptAudios;
            case video: return ScriptVideo;
            case scripttype: return ScriptType;
            case karaokemakers: return KaraokeMakers;
            default: return "";
        }
    }
}
