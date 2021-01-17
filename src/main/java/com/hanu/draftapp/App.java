package com.hanu.draftapp;

import com.hanu.draftapp.utils.ServerContainer;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception {
        ServerContainer.getInstance("tomcat")
                        .defaultConfigure()
                        .addServlets("com.hanu.draftapp.servlets")
                        .start();
    }
}
