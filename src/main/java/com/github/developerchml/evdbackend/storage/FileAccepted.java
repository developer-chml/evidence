package com.github.developerchml.evdbackend.storage;

public enum FileAccepted {
    PDF, MPEG, MP4, XML;

    public static FileAccepted of(String str) {
        if (str == null)
            return null;

        for(FileAccepted e : FileAccepted.values()){
            if(str.equalsIgnoreCase(e.name())) {
                return e;
            }
        }
        return null;
    }
}
