package com.crowd.util;

/**
 * Created by marce on 3/21/18.
 */
public class Constants {

    public enum ResponseConstants {
        SUCCESS("Execution Successful"), FAILURE("Execution Unsuccessful"), EXISTS("This word already exists");

        private String descrip;

        ResponseConstants(final String descrip) {
            this.descrip = descrip;
        }

        public String getDescrip() {
            return descrip;
        }
    }
}
