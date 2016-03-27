/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.programla.todomore;

/**
 *
 * @author kenan.erarslan
 */
public enum State {
    INITIAL(25), WORKING(25), SHORT_BREAK(5), LONG_BREAK(10), JUST_WORK(120), JUST_BREAK(120);
    
    private int time;

    State(int time) {
        this.time = time;
    }

    public int time() {
        return time;
    }
    
    public int timeAsSecond() {
        return time * 60;
    }
}
