package com.revature.weekNine.inversionOfControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyClass {
    private final MyDependency myDependency;

    @Autowired
    public MyClass(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    public MyDependency getMyDependency() {
        return this.myDependency;
    }
}
