package com.basewin.kms.util;

import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

public class MyTrustManager implements X509TrustManager {

    public MyTrustManager(){}

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        System.out.println("check");
        if(x509Certificates==null||x509Certificates.length==0||s==null||s.length()==0) throw new IllegalArgumentException();
        KeyStore store=getKeyStore();
        boolean pass=false;
        try {
            for(X509Certificate certificate:x509Certificates){
                certificate.checkValidity();
                String theAlias = store.getCertificateAlias(certificate);
                if(theAlias!=null)pass=true;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        System.out.println("pass "+pass);
        if(!pass)throw new  CertificateException();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        if(x509Certificates==null||x509Certificates.length==0||s==null||s.length()==0) throw new IllegalArgumentException();
        for(X509Certificate certificate:x509Certificates){
            certificate.checkValidity();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        ArrayList<X509Certificate> trusts=new ArrayList<X509Certificate>();
        try {
            KeyStore store=getKeyStore();
            Enumeration<String> alias = store.aliases();
            while (alias.hasMoreElements()){
                String name = alias.nextElement();
                if(store.isCertificateEntry(name)){
                    X509Certificate trust = (X509Certificate) store.getCertificate(name);
                    trusts.add(trust);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        X509Certificate[] trustsArr = trusts.toArray(new X509Certificate[0]);
        System.out.println("return trust array "+trustsArr.length);
        return trustsArr;
    }

    private KeyStore getKeyStore() throws CertificateException {
        try {
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
            store.load(new FileInputStream("ssl/server/server.keystore"),"123456".toCharArray());
            return store;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CertificateException();
        }
    }
}