/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fernanda
 */
public class Transition {
    Character l;
    Integer target;
    
    public Transition(Character lab, Integer target) 
    { this.l = lab; this.target = target; }
    
    public String toString() {
      return "-" + l + "-> " + target;
    }

    public Character getL() {
        return l;
    }

    public void setL(Character l) {
        this.l = l;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
    
    
    
  }
    

