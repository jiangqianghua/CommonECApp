package com.jqh.jqh.deletegates.web.event;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.HashMap;

public class EventManager {
    private static final HashMap<String ,Event> EVENTS = new HashMap<>();

    private EventManager(){

    }

    private static class Holder{
        private static final EventManager INSTANCE = new EventManager();
    }

    public static  EventManager getInstance(){
        return Holder.INSTANCE;
    }

    public EventManager addEvent(@NotNull String name , @NotNull Event event){
        EVENTS.put(name,event);
        return this ;
    }

    public Event createEvent(@NotNull String action){
        final Event event = EVENTS.get(action);
        if(event == null){
            return new UndefineEvent();
        }
        return event;
    }
}
