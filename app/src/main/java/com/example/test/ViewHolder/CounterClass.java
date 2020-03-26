package com.example.test.ViewHolder;

public class CounterClass {
    int count=0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int addcount()
    {
        this.count++;
        return count;
    }
    public int mincount()
    {
        if (count >1){
            this.count--;
        }
        return count;
    }


}
