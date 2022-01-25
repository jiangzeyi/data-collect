package com.ivw.task.handler;

public class DefaultHandler extends AbstractHandler{
    @Override
    void startBefore() {

    }

    @Override
    Object execute(Object data) {
        return data;
    }

    @Override
    void finishAfter() {

    }
}
