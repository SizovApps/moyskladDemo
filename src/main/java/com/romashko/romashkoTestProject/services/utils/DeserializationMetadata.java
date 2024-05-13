package com.romashko.romashkoTestProject.services.utils;


public class DeserializationMetadata {
    private final boolean isUpdate;

    public DeserializationMetadata(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
}