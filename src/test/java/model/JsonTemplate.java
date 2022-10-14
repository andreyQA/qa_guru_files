package model;

import java.util.List;

public class JsonTemplate {
    public String dishname;
    public String difficult;
    public String availability;
    public int price;
    public Ingridients ingridients;
    public class Ingridients {
        public boolean meat;
        public boolean vegetables;
        public boolean oil;
        public boolean salt;
        public boolean pepper;
    }

}
