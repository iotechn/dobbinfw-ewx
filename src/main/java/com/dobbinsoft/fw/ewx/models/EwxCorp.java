package com.dobbinsoft.fw.ewx.models;

public interface EwxCorp {

    String getCorpId();

    String getArchiveSecret();

    String getArchiveToken();

    String getArchiveAesKey();

    long getArchiveSdk();

    void setArchiveSdk(long sdk);

}
