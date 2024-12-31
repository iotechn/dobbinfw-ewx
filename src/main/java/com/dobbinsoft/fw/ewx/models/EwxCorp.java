package com.dobbinsoft.fw.ewx.models;

public interface EwxCorp {

    String getCorpId();

    String getArchiveSecret();

    String getArchiveToken();

    String getArchiveAesKey();

    String getArchivePrivateKey();

    public default String archiveSdkKey() {
        return getCorpId() + "---" + getArchiveSecret();
    }

}
