package com.github.developerchml.evdbackend.storage;

public enum IMGAccepted {
    PNG, JPG, JPEG;

    public static IMGAccepted of(String str) {
        if (str == null)
            return null;

        for(IMGAccepted e : IMGAccepted.values()){
            if(str.equalsIgnoreCase(e.name())) {
                return e;
            }
        }
        return null;
    }
}
